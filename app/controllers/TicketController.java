package controllers;

import dtos.TicketDTO;
import forms.TicketForm;
import models.Ticket;
import play.Logger;
import play.api.i18n.Lang;
import play.data.*;
import play.db.jpa.Transactional;
import play.i18n.MessagesApi;
import play.mvc.*;
import services.TicketService;

import javax.inject.Inject;

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
    public TicketController(FormFactory formFactory, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
    }

    /**
     * トップページ
     *
     * @return
     */
    public Result index() {
        return Results.ok(views.html.ticket.index.render());
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
     * チケット入力内容の確認画面
     *
     * @return バリデーション済みのフォームデータ
     */
    public Result confirm() {

        Form<TicketForm> f = formFactory.form(TicketForm.class).bindFromRequest();

        if(f.hasErrors()) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400"), f.errorsAsJson());
            return Results.badRequest(views.html.ticket.create.render(f));
        }

        return Results.ok(views.html.ticket.confirm.render(f));

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

            return Results.badRequest(views.html.ticket.confirm.render(f));
        }

        TicketForm ticketForm = f.get();
        Ticket newTicket = TicketDTO.convertToEntity(ticketForm);
        Long userId = Long.valueOf(session("userID"));

        ticketService.createTicket(newTicket, userId);

        flash("created", "チケットを作成しました");
        return redirect("/top");
    }
    
}
