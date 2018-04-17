package controllers;

import forms.AdminForm;
import models.User;
import play.Logger;
import play.data.*;
import play.db.jpa.Transactional;
import play.i18n.MessagesApi;
import play.mvc.*;
import services.UserService;

import javax.inject.Inject;

/**
 * 管理画面
 *
 * @author arapiku
 */
public class AdminController extends Controller {

    private final FormFactory formFactory;

    private final MessagesApi messagesApi;

    @Inject
    private UserService userService;

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
        System.out.println(user.admin);
        // 管理ユーザーチェック
        if (user.admin == null) {
            user.admin = false;
        }
        System.out.println(user.admin);
        if(!user.admin) {
            Logger.warn("不正なアクセスです。{}は管理ユーザーではありません。", f.get().userName);
            return Results.badRequest(views.html.admin.login.render(f));
        }

        return Results.ok(views.html.admin.index.render());
    }
}
