package controllers;

import play.mvc.*;

/**
 * チケット情報に関するコントローラ
 *
 * @author arapiku
 */
public class TicketController extends Controller {

    /**
     * トップページの表示
     * （今は表示しか機能させてないのでのちのち修正必要）
     *
     * @return
     */
    public Result index() {
        return Results.ok(views.html.index.render());
    }
    
}
