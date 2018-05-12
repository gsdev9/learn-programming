package controllers;

import play.mvc.*;

import static play.mvc.Controller.session;
import static play.mvc.Results.redirect;

/**
 * ログアウト処理
 *
 * @author arapiku
 */
public class LogoutController {

    /**
     * セッション破棄とトップページへのリダイレクト
     *
     * @return
     */
    public Result logout() {
        session().remove("userID");
        session().remove("thumbnailPath");
        return redirect("/");
    }

}
