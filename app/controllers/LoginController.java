package controllers;

import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.i18n.Lang;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
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
        session().remove("userID");
        session().remove("thumbnailPath");

        return Results.ok(views.html.login.index.render());
    }

    /**
     * ログインフォーム画面の出力
     *
     * @return
     */
    public Result loginForm() {
        Form<User> f = formFactory.form(User.class);

        return Results.ok(views.html.login.login.render(f));
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

        if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
            flash("error", messagesApi.get(Lang.forCode("ja"), "login.errors.400.nullorempty"));
            return Results.redirect("/login");
        }

        User user = userService.findByUserNameAndPassword(userName, password);
        if (user == null) {
            flash("error", messagesApi.get(Lang.forCode("ja"),"login.errors.400.nouser"));
            return Results.redirect("/login");
        }

        session("userID", String.valueOf(user.userId));
        session("thumbnailPath", user.thumbnailPath);
        flash("signin", "ログインしました");
        return redirect("/top");
    }

}
