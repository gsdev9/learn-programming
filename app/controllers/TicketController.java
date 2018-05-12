package controllers;

import com.google.common.base.Strings;
import dtos.TicketDTO;
import dtos.utils.DateUtils;
import forms.MessageForm;
import forms.TicketForm;
import models.*;
import org.springframework.beans.BeanUtils;
import play.Logger;
import play.api.i18n.Lang;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * チケット情報に関するコントローラ
 *
 * @author arapiku
 */
public class TicketController extends Controller {

    private final FormFactory formFactory;

    private final MessagesApi messagesApi;

    private final JPAApi jpa;

    @Inject
    private TicketService ticketService;

    @Inject
    private PurchasedTicketService purchasedTicketService;

    @Inject
    private TicketLabelService ticketLabelService;

    @Inject
    private ChatRoomService chatRoomService;

    @Inject
    private MessageService messageService;

    @Inject
    private ReviewService reviewService;


    @Inject
    public TicketController(FormFactory formFactory, MessagesApi messagesApi, JPAApi jpa) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
        this.jpa = jpa;
    }

    /**
     * チケットオーナー判定
     *
     * @param ticket
     * @param userId
     * @return
     */
    private boolean checkUser(Ticket ticket, Long userId) {
        if(!ticket.getUser().userId.equals(userId)) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400", "userId: " + userId));
            Controller.flash("badRequest", "不正なアクセスです");
            return true;
        }
        return false;
    }

    /**
     * トップページ
     * 全てのチケットを新着順に表示する
     * Todo 表示順を考慮する
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Result index() {
        List<Ticket> tickets = ticketService.findAll();
        if(tickets.isEmpty()) {
            Controller.flash("notFound", "チケットが見つかりませんでした。");
            Logger.warn("Nothing Tickets.");
            Results.notFound(views.html.ticket.index.render(tickets));
        }
        return Results.ok(views.html.ticket.index.render(tickets));
    }

    /**
     * フォーム入力によるチケット検索
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Result searchByInput() {
        DynamicForm f = formFactory.form().bindFromRequest();

        String input = Strings.nullToEmpty(f.get("input"));
        if(input.isEmpty()) {
            return Results.ok();
        }

        List<Ticket> tickets = ticketService.findByTitleOrBody(input);

        if (tickets.isEmpty()) {
            Logger.warn("検索結果に該当するチケットがありませんでした.。input={}", input);
            return Results.ok(Json.toJson(tickets));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("tickets", tickets);

        return Results.ok(Json.toJson(map));
    }

    /**
     * チケットのカテゴリ検索
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Result searchByCategory() {
        DynamicForm f = formFactory.form().bindFromRequest();

        String category = Strings.nullToEmpty(f.get("category"));
        if(category.isEmpty()) {
            return Results.ok();
        }

        List<Ticket> tickets = ticketService.findByCategory(category);

        if (tickets.isEmpty()) {
            Logger.warn("検索結果に該当するチケットがありませんでした.。category={}", category);
            return Results.ok(Json.toJson(tickets));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("tickets", tickets);

        return Results.ok(Json.toJson(map));
    }

    /**
     * チケットの単一ページ
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Result single(Long id) {
        Ticket ticket = ticketService.findById(id);
        Long userId = Long.valueOf(Controller.session().get("userID"));
        List<UserReview> userReviews = reviewService.findByTicketId(id);
        if(ticket == null) {
            Logger.warn("id={}'s ticket is not founded.", id);
            List<Ticket> tickets = ticketService.findAll();
            Results.notFound(views.html.ticket.index.render(tickets));
        }
        //売れたチケット
        List<PurchasedTicket> saledTickets = purchasedTicketService.findByTicketId(id);
        List<PurchasedTicket> purchasedTickets = purchasedTicketService.findByBuyerId(userId);
        //購入したチケット
        List<PurchasedTicket> buyTickets = new ArrayList<>();
        if (!purchasedTickets.isEmpty()) {
            for (PurchasedTicket purchasedTicket : purchasedTickets) {
                if (purchasedTicket.getStatus() == false) {
                    buyTickets.add(purchasedTicket);
                }
            }
        }
        if (ticket.getUser().getUserId() != userId) {
            saledTickets = new ArrayList<>();
        }
        return Results.ok(views.html.ticket.single.render(ticket, userReviews, saledTickets, buyTickets));
    }

    /**
     * チケット作成のフォーム表示
     *
     * @return フォーム
     */
    public Result create() {

        Form<TicketForm> ticketForm = formFactory.form(TicketForm.class);

        return Results.ok(views.html.ticket.create.render(ticketForm));

    }

    /**
     * チケット作成内容の確認画面
     *
     * @return バリデーション済みのフォームデータ
     */
    public Result createConfirm() {

        Form<TicketForm> ticketForm = formFactory.form(TicketForm.class).bindFromRequest();

        if(ticketForm.hasErrors()) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400"), ticketForm.errorsAsJson());
            return Results.badRequest(views.html.ticket.create.render(ticketForm));
        }

        return Results.ok(views.html.ticket.create_confirm.render(ticketForm));

    }

    /**
     * チケットの作成
     *
     * @return
     */
    @Transactional
    public Result regist() {

        Form<TicketForm> ticketForm = formFactory.form(TicketForm.class).bindFromRequest();

        if(ticketForm.hasErrors()) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400"), ticketForm.errorsAsJson());
            return Results.badRequest(views.html.ticket.create.render(ticketForm));
        }

        TicketForm ticketFormGet = ticketForm.get();

        // チケットラベルを作成
        TicketLabel ticketLabel = new TicketLabel();
        ticketLabel.c = ticketFormGet.c;
        ticketLabel.cPlusPlus = ticketFormGet.cPlusPlus;
        ticketLabel.cSharp = ticketFormGet.cSharp;
        ticketLabel.java = ticketFormGet.java;
        ticketLabel.javaScript = ticketFormGet.javaScript;
        ticketLabel.php = ticketFormGet.php;
        ticketLabel.ruby = ticketFormGet.ruby;
        ticketLabel.python = ticketFormGet.python;
        ticketLabel.perl = ticketFormGet.perl;
        ticketLabel.r = ticketFormGet.r;
        ticketLabel.go = ticketFormGet.go;
        ticketLabel.scala = ticketFormGet.scala;
        ticketLabel.objectiveC = ticketFormGet.objectiveC;
        ticketLabel.swift = ticketFormGet.swift;
        ticketLabel.kotlin = ticketFormGet.kotlin;
        ticketLabel.scratch = ticketFormGet.scratch;
        ticketLabel.blockly = ticketFormGet.blockly;
        ticketLabel.sqlLang = ticketFormGet.sqlLang;
        jpa.em().persist(ticketLabel);

        // チケットを作成
        Ticket ticket = new Ticket();
        Ticket newTicket = TicketDTO.convertToEntity(ticket, ticketFormGet, ticketLabel);

        Long userId = Long.valueOf(Controller.session("userID"));

        ticketService.createTicket(newTicket, userId);

        Controller.flash("created", "チケットを作成しました");
        return Results.redirect("/top");
    }

    /**
     * チケットの編集画面
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Result edit(Long id) {

        Ticket ticket = ticketService.findById(id);
        TicketLabel ticketLabel = ticketLabelService.findById(ticket.getTicketLabel().TicketLabelId);
        TicketForm formData = new TicketForm();

        BeanUtils.copyProperties(ticket, formData);

        formData.date = DateUtils.toStringFromLocalDate(ticket.date, "uuuu-MM-dd");
        formData.startAt = DateUtils.toStringFromLocalTime(ticket.startAt, "HH:mm");
        formData.endAt = DateUtils.toStringFromLocalTime(ticket.endAt, "HH:mm");
        formData.price = String.valueOf(ticket.price);
        formData.c = ticketLabel.c;
        formData.cPlusPlus = ticketLabel.cPlusPlus;
        formData.cSharp = ticketLabel.cSharp;
        formData.java = ticketLabel.java;
        formData.javaScript = ticketLabel.javaScript;
        formData.php = ticketLabel.php;
        formData.ruby = ticketLabel.ruby;
        formData.python = ticketLabel.python;
        formData.perl = ticketLabel.perl;
        formData.r = ticketLabel.r;
        formData.go = ticketLabel.go;
        formData.scala = ticketLabel.scala;
        formData.objectiveC = ticketLabel.objectiveC;
        formData.swift = ticketLabel.swift;
        formData.kotlin = ticketLabel.kotlin;
        formData.scratch = ticketLabel.scratch;
        formData.blockly = ticketLabel.blockly;
        formData.sqlLang = ticketLabel.sqlLang;

        Form<TicketForm> f = formFactory.form(TicketForm.class).fill(formData);

        return Results.ok(views.html.ticket.edit.render(f, id));

    }

    /**
     * チケット編集内容の確認画面
     *
     * @return バリデーション済みのフォームデータ
     */
    public Result editConfirm(Long id) {

        Form<TicketForm> f = formFactory.form(TicketForm.class).bindFromRequest();

        if(f.hasErrors()) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400"), f.errorsAsJson());
            return Results.badRequest(views.html.ticket.edit.render(f, id));
        }

        return Results.ok(views.html.ticket.edit_confirm.render(f, id));

    }

    /**
     * チケットの更新
     *
     * @param id
     * @return
     */
    @Transactional
    public Result update(Long id) {
        Form<TicketForm> f = formFactory.form(TicketForm.class).bindFromRequest();

        if (f.hasErrors()) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400", f.errorsAsJson()));
            return Results.badRequest(views.html.ticket.edit_confirm.render(f, id));
        }

        Ticket ticket = ticketService.findById(id);
        Long userId = Long.valueOf(Controller.session("userID"));

        if(checkUser(ticket, userId)) {
            List<Ticket> tickets = ticketService.findAll();
            return Results.badRequest(views.html.ticket.index.render(tickets));
        }

        Ticket updateTicket = TicketDTO.ticketLabelToEntityForUpdate(ticket, f.get());
        ticketService.updateTicket(updateTicket);

        Controller.flash("updated", "チケットを更新しました");
        return Results.redirect("/top");

    }

    /**
     * チケットの削除確認
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Result delete(Long id) {

        Ticket ticket = ticketService.findById(id);
        TicketForm formData = new TicketForm();

        BeanUtils.copyProperties(ticket, formData);

        formData.date = DateUtils.toStringFromLocalDate(ticket.date, "uuuu-MM-dd");
        formData.startAt = DateUtils.toStringFromLocalTime(ticket.startAt, "HH:mm");
        formData.endAt = DateUtils.toStringFromLocalTime(ticket.endAt, "HH:mm");
        formData.price = String.valueOf(ticket.price);

        Form<TicketForm> f = formFactory.form(TicketForm.class).fill(formData);

        return Results.ok(views.html.ticket.delete.render(f, id));

    }
    
    /**
     * チケットの削除
     *
     * @param id
     * @return
     */
    @Transactional
    public Result destroy(Long id) {

        Ticket ticket = ticketService.findById(id);
        Long userId = Long.valueOf(Controller.session("userID"));

        if(checkUser(ticket, userId)) {
            List<Ticket> tickets = ticketService.findAll();
            return Results.badRequest(views.html.ticket.index.render(tickets));
        }

        ticketService.deleteTicket(ticket);

        Controller.flash("deleted", "チケットを削除しました");
        return Results.redirect("/top");

    }

    /**
     * チケット購入画面
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Result purchase(Long id) {

        Ticket ticket = ticketService.findById(id);
        Form<MessageForm> f = formFactory.form(MessageForm.class);
        return Results.ok(views.html.ticket.purchase.render(ticket, f));
    }

    /** 
     * チケットの購入成立
     *
     * @param ticketId
     * @return
     */
    @Transactional
    public Result appoint(Long ticketId) {

        Form<MessageForm> f = formFactory.form(MessageForm.class).bindFromRequest();

        Long userId = Long.valueOf(Controller.session("userID"));

        ChatRoom chatRoom = new ChatRoom();

        //チャットルームの作成
        chatRoomService.registChatRoom(chatRoom);

        // 購入済みチケットの登録
        PurchasedTicket purchasedTicket = purchasedTicketService.create(ticketId, userId, chatRoom);

        // メッセージの登録
        messageService.create(f.get().getMessage(), userId, purchasedTicket);

        Controller.flash("appointed", "授業が成立しました！");
        return Results.redirect("/top");

    }

}
