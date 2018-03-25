package controllers;

import models.User;
import play.data.*;
import play.db.jpa.Transactional;
import play.i18n.*;
import play.mvc.*;
import services.UserService;

import javax.inject.Inject;

/**
 * ログイン処理に関するコントローラ
 *
 * @author arapiku
 */
@Transactional
public class LoginController extends Controller {

    private final FormFactory formFactory;

    @Inject
    private UserService userService;

    @Inject
    private MessagesApi messagesApi;

    @Inject
    public LoginController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result index() {
        return Results.ok(views.html.index.render());
    }

    /**
     * ログインフォーム画面の出力
     *
     * @return
     */
    public Result loginForm() {
        Form<User> f = formFactory.form(User.class);

        return Results.ok(views.html.login.render(f));
    }

    /**
     * ログイン処理
     *
     * @return
     */
    public Result login() {
        Form<User> f = formFactory.form(User.class).bindFromRequest();
        String userName = f.get().userName;
        String password = f.get().password;
        // ここに null or empty チェック必要ぽい
        if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
            flash("error", messagesApi.get(Lang.forCode("ja"), "login.errors.400.nullorempty"));
            return Results.badRequest(views.html.login.render(f));
        }

        User user = userService.findByUserNameAndPassword(userName, password);
        System.out.println(user);

        if (user == null) {
            flash("error", messagesApi.get(Lang.forCode("ja"),"login.errors.400.nouser"));
            return Results.badRequest(views.html.login.render(f));
        }

        session("userID", String.valueOf(user.userId));
        return redirect("/top");
    }

}
