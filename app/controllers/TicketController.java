package controllers;

import dtos.TicketDTO;
import dtos.utils.DateUtils;
import forms.*;
import models.*;
import org.springframework.beans.BeanUtils;
import play.Logger;
import play.api.i18n.Lang;
import play.data.*;
import play.db.jpa.Transactional;
import play.i18n.MessagesApi;
import play.mvc.*;
import services.*;

import javax.inject.Inject;
import java.util.List;

/**
 * チケット情報に関するコントローラ
 *
 * @author arapiku
 */
public class TicketController extends Controller {

    private final FormFactory formFactory;

    private final MessagesApi messagesApi;

    @Inject
    private TicketService ticketService;

    @Inject
    private PurchasedTicketService purchasedTicketService;

    @Inject
    private MessageService messageService;

    @Inject
    public TicketController(FormFactory formFactory, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
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
            flash("notFound", "チケットが見つかりませんでした。");
            Logger.warn("Nothing Tickets.");
            Results.notFound(views.html.ticket.index.render(tickets));
        }
        return Results.ok(views.html.ticket.index.render(tickets));
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
        if(ticket == null) {
            Logger.warn("id={}'s ticket is not founded.", id);
            List<Ticket> tickets = ticketService.findAll();
            Results.notFound(views.html.ticket.index.render(tickets));
        }
        return Results.ok(views.html.ticket.single.render(ticket));
    }

    /**
     * チケット作成のフォーム表示
     *
     * @return フォーム
     */
    public Result create() {

        Form<TicketForm> f = formFactory.form(TicketForm.class);

        return Results.ok(views.html.ticket.create.render(f));

    }

    /**
     * チケット作成内容の確認画面
     *
     * @return バリデーション済みのフォームデータ
     */
    public Result createConfirm() {

        Form<TicketForm> f = formFactory.form(TicketForm.class).bindFromRequest();

        if(f.hasErrors()) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400"), f.errorsAsJson());
            return Results.badRequest(views.html.ticket.create.render(f));
        }

        return Results.ok(views.html.ticket.create_confirm.render(f));

    }

    /**
     * チケットの作成
     *
     * @return
     */
    @Transactional
    public Result regist() {

        Form<TicketForm> f = formFactory.form(TicketForm.class).bindFromRequest();

        if(f.hasErrors()) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400", f.errorsAsJson()));

            return Results.badRequest(views.html.ticket.create_confirm.render(f));
        }

        TicketForm ticketForm = f.get();
        Ticket ticket = new Ticket();
        Ticket newTicket = TicketDTO.convertToEntity(ticket, ticketForm);
        Long userId = Long.valueOf(session("userID"));

        ticketService.createTicket(newTicket, userId);

        flash("created", "チケットを作成しました");
        return redirect("/top");
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
        TicketForm formData = new TicketForm();

        BeanUtils.copyProperties(ticket, formData);

        formData.date = DateUtils.toStringFromLocalDate(ticket.date, "uuuu-MM-dd");
        formData.startAt = DateUtils.toStringFromLocalTime(ticket.startAt, "HH:mm");
        formData.endAt = DateUtils.toStringFromLocalTime(ticket.endAt, "HH:mm");
        formData.price = String.valueOf(ticket.price);

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
        Ticket updateTicket = TicketDTO.convertToEntity(ticket, f.get());
        ticketService.updateTicket(updateTicket);

        flash("updated", "チケットを更新しました");
        return redirect("/top");

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

        Long userId = Long.valueOf(session("userID"));

        // 購入済みチケットの登録
        PurchasedTicket purchasedTicket = purchasedTicketService.create(ticketId, userId);

        // メッセージの登録
        messageService.create(f.get().getMessage(), userId, purchasedTicket);

        flash("appointed", "授業が成立しました！");
        return redirect("/top");

    }
    
}
