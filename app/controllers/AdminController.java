package controllers;

import dtos.*;
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
 * 管理画面
 *
 * @author arapiku
 */
// TODO 管理ユーザーとUUIDが一致しなければアクセスさせないアノテーション
public class AdminController extends Controller {

    private final FormFactory formFactory;

    private final MessagesApi messagesApi;

    @Inject
    private UserService userService;

    @Inject
    private UserDTO userDTO;

    @Inject
    private TicketService ticketService;

    @Inject
    private TicketLabelService ticketLabelService;

    @Inject
    public AdminController(FormFactory formFactory, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
    }

    /**
     * ログインフォームの出力
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Result index() {
        Form<AdminForm> f = formFactory.form(AdminForm.class);

        return Results.ok(views.html.admin.login.render(f));
    }

    /**
     * ログイン
     *
     * @return
     */
    @Transactional
    public Result login() {
        Form<AdminForm> f = formFactory.form(AdminForm.class).bindFromRequest();

        if(f.hasErrors()) {
            Logger.warn("ユーザー名とパスワードが一致しません", f.errorsAsJson());
            return Results.badRequest(views.html.admin.login.render(f));
        }

        User user = userService.findByUserNameAndPassword(f.get().userName, f.get().password);
        // ユーザーの存在チェック
        if(user == null) {
            Logger.warn("ユーザーが存在しません", f.get().userName);
            return Results.forbidden(views.html.admin.login.render(f));
        }

        // 管理ユーザーチェック
        if (user.admin == null) {
            user.admin = false;
        }

        if(!user.admin) {
            Logger.warn("不正なアクセスです。{}は管理ユーザーではありません。", f.get().userName);
            return Results.badRequest(views.html.admin.login.render(f));
        }

        return Results.ok(views.html.admin.index.render());
    }

    /**
     * ユーザー一覧
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Result showUsers() {
        List<User> userList = userService.findAll();
        if(userList.isEmpty()) {
            flash("noUsers", "ユーザーが存在しません");
        }

        return Results.ok(views.html.admin.users.index.render(userList));
    }

    /**
     * ユーザー情報の編集
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Result editUser(Long id) {
        User user = userService.findById(id);
        if(user == null) {
            Logger.warn("ユーザーが見つかりません。id={}", id);
            return redirect("/admin/users");
        }
        Form<User> f = formFactory.form(User.class).fill(user);

        return Results.ok(views.html.admin.users.edit.render(f));
    }

    /**
     * ユーザー情報の更新
     *
     * @param id
     * @return
     */
    @Transactional
    public Result updateUser(Long id) {
        Form<User> f = formFactory.form(User.class).bindFromRequest();

        if(f.hasErrors()) {
            Logger.warn("不正なリクエストです", f.errorsAsJson());
            return Results.badRequest(views.html.admin.users.edit.render(f));
        }

        User oldUser = userService.findById(id);
        if(oldUser == null) {
            Logger.warn("ユーザーが存在しません", f.get().userName);
            return Results.forbidden(views.html.admin.users.edit.render(f));
        }

        User newUser = userDTO.adminUserDto(oldUser, f);
        userService.updateUser(newUser);

        return redirect("/admin/users");
    }

    /**
     * ユーザー削除確認画面
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Result deleteUser(Long id) {
        User user = userService.findById(id);
        if(user == null) {
            Logger.warn("ユーザーが見つかりません。id={}", id);
            return redirect("/admin/users");
        }
        Form<User> f = formFactory.form(User.class).fill(user);

        return Results.ok(views.html.admin.users.delete.render(f));
    }

    /**
     * ユーザーの削除
     *
     * @param id
     * @return
     */
    @Transactional
    public Result destroyUser(Long id) {
        User user = userService.findById(id);
        if(user == null) {
            Logger.warn("ユーザーが見つかりません。id={}", id);
            return redirect("/admin/users");
        }
        userService.deleteUser(user);

        return redirect("/admin/users");
    }

    /**
     * チケット一覧
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Result showTickets() {
        List<Ticket> ticketList = ticketService.findAll();
        if(ticketList.isEmpty()) {
            flash("noTickets", "チケットが存在しません");
        }

        return Results.ok(views.html.admin.tickets.index.render(ticketList));
    }

    /**
     * チケット編集
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Result editTicket(Long id) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            Logger.warn("チケットが見つかりません。id={}", id);
            return redirect("/admin/tickets");
        }
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

        return Results.ok(views.html.admin.tickets.edit.render(f, id));
    }

    /**
     * チケット編集確認
     *
     * @return
     */
    public Result confirmEditedTicket(Long id) {
        Form<TicketForm> f = formFactory.form(TicketForm.class).bindFromRequest();

        if (f.hasErrors()) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400"), f.errorsAsJson());
            return Results.badRequest(views.html.admin.tickets.edit.render(f, id));
        }

        return Results.ok(views.html.admin.tickets.edit_confirm.render(f, id));
    }

    /**
     * チケッtの更新
     *
     * @param id
     * @return
     */
    @Transactional
    public Result updateTicket(Long id) {
        Form<TicketForm> f = formFactory.form(TicketForm.class).bindFromRequest();

        if (f.hasErrors()) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400"), f.errorsAsJson());
            return Results.badRequest(views.html.admin.tickets.edit_confirm.render(f, id));
        }

        Ticket ticket = ticketService.findById(id);
        Ticket updateTicket = TicketDTO.ticketLabelToEntityForUpdate(ticket, f.get());
        ticketService.updateTicket(updateTicket);

        return redirect("/admin/tickets");
    }

    /**
     * チケット削除確認
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Result deleteTicket(Long id) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            Logger.warn("チケットが見つかりません。id={}", id);
            return redirect("/admin/tickets");
        }

        TicketForm formData = new TicketForm();

        BeanUtils.copyProperties(ticket, formData);

        formData.date = DateUtils.toStringFromLocalDate(ticket.date, "uuuu-MM-dd");
        formData.startAt = DateUtils.toStringFromLocalTime(ticket.startAt, "HH:mm");
        formData.endAt = DateUtils.toStringFromLocalTime(ticket.endAt, "HH:mm");
        formData.price = String.valueOf(ticket.price);

        Form<TicketForm> f = formFactory.form(TicketForm.class).fill(formData);

        return Results.ok(views.html.admin.tickets.delete.render(f, id));
    }

    /**
     * チケットの削除
     *
     * @param id
     * @return
     */
    @Transactional
    public Result destroyTicket(Long id) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            Logger.warn("チケットが見つかりません。id={}", id);
            return redirect("/admin/tickets");
        }

        ticketService.deleteTicket(ticket);

        return redirect("/admin/tickets");
    }
}
