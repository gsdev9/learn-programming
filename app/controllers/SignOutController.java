package controllers;

import models.User;
import play.Logger;
import play.api.i18n.Lang;
import play.data.*;
import play.db.jpa.Transactional;
import play.i18n.MessagesApi;
import play.mvc.*;
import services.UserService;

import javax.inject.Inject;

/**
 * 会員退会機能
 *
 * @author arapiku
 */
public class SignOutController extends Controller {

    private final DynamicForm dynamicForm;

    private final MessagesApi messagesApi;

    @Inject
    private UserService userService;

    @Inject
    public SignOutController(FormFactory formFactory, MessagesApi messagesApi) {
        this.dynamicForm = formFactory.form();
        this.messagesApi = messagesApi;
    }

    /**
     * 退会するかTOPに戻るかを提示する画面
     *
     * @return
     */
    public Result index() {
        return Results.ok(views.html.signout.index.render());
    }

    /**
     * ユーザーの削除
     *
     * @return
     */
    @Transactional
    public Result deleteUser() {
        DynamicForm requestData = dynamicForm.bindFromRequest();

        // TODO nullチェック必要 しかしのちにアノテーションでページアクセス制御して対応
        Long id = Long.valueOf(session().get("userID"));
        User user = userService.findById(id);

        // TODO キャッシュしたUUIDとユーザーIDの照合 or password認証

        if (user == null) {
            Logger.warn(messagesApi.get(Lang.apply(Lang.defaultLang().code()), "client.errors.400"));
            return Results.badRequest(views.html.ticket.index.render());
        }

        userService.deleteUser(user);
        session().remove("userID");

        flash("userDeleted", messagesApi.get(Lang.apply(Lang.defaultLang().code()),"signout.status.204"));

        return redirect("/");
    }

}
