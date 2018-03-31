package controllers;

import models.Ticket;
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

        Form<Ticket> f = formFactory.form(Ticket.class);

        return Results.ok(views.html.ticket.index.render(f));

    }


    /**
     * チケット入力内容の確認画面
     *
     * @return
     */
    public Result confirm() {

        Form<Ticket> f = formFactory.form(Ticket.class).bindFromRequest();
        String startTime = String.valueOf(f.get().startTime);
        String endTime = String.valueOf(f.get().endTime);
        String price = String.valueOf(f.get().price);
        String title = f.get().title;
        String body = f.get().body;

        Map<String, String> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("price", price);
        map.put("title", title);
        map.put("body", body);

        return Results.ok(views.html.ticket.confirm.render(map));

    }
    
}
