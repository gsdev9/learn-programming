package controllers;

import forms.TicketForm;
import play.Logger;
import play.api.i18n.Lang;
import play.data.*;
import play.i18n.*;
import play.mvc.*;

import javax.inject.Inject;
import java.util.*;

/**
 * チケット情報に関するコントローラ
 *
 * @author arapiku
 */
public class TicketController extends Controller {

    private final FormFactory formFactory;

    private final MessagesApi messagesApi;

    @Inject
    public TicketController(FormFactory formFactory, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
    }

    /**
     * チケット作成のフォーム表示
     *
     * @return
     */
    public Result index() {

        Form<TicketForm> f = formFactory.form(TicketForm.class);

        return Results.ok(views.html.ticket.index.render(f));

    }


    /**
     * チケット入力内容の確認画面
     *
     * @return
     */
    public Result confirm() {

        Form<TicketForm> f = formFactory.form(TicketForm.class).bindFromRequest();

        if(f.hasErrors()) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400"), f.errorsAsJson());
            return Results.badRequest(views.html.ticket.index.render(f));
        }

        String date = String.valueOf(f.get().date);
        String startAt = String.valueOf(f.get().startAt);
        String endAt = String.valueOf(f.get().endAt);
        String price = String.valueOf(f.get().price);
        String title = f.get().title;
        String body = f.get().body;

        Map<String, String> map = new HashMap<>();
        map.put("date", date);
        map.put("startTime", startAt);
        map.put("endTime", endAt);
        map.put("price", price);
        map.put("title", title);
        map.put("body", body);

        return Results.ok(views.html.ticket.confirm.render(map));

    }
    
}
