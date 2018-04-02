package controllers;

import forms.TicketForm;
import play.Logger;
import play.data.*;
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

    @Inject
    public TicketController(FormFactory formFactory) {
        this.formFactory = formFactory;
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
            Logger.warn("client.errors.400", f.errorsAsJson());
        }
        System.out.println(f.get().startAt);
        System.out.println(f.get().endAt);
        System.out.println(f.get().price);
        System.out.println(f.get().title);
        System.out.println(f.get().body);

        String startAt = String.valueOf(f.get().startAt);
        String endAt = String.valueOf(f.get().endAt);
        String price = String.valueOf(f.get().price);
        String title = f.get().title;
        String body = f.get().body;

        Map<String, String> map = new HashMap<>();
        map.put("startTime", startAt);
        map.put("endTime", endAt);
        map.put("price", price);
        map.put("title", title);
        map.put("body", body);

        return Results.ok(views.html.ticket.confirm.render(map));

    }
    
}
