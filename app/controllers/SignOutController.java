package controllers;

import models.User;
import play.Logger;
import play.api.i18n.Lang;
import play.cache.AsyncCacheApi;
import play.data.*;
import play.db.jpa.Transactional;
import play.i18n.MessagesApi;
import play.mvc.*;
import services.UserService;

import javax.inject.Inject;

public class SignOutController extends Controller {

    private final DynamicForm dynamicForm;

    private final AsyncCacheApi asyncCacheApi;

    private final MessagesApi messagesApi;

    @Inject
    private UserService userService;

    @Inject
    public SignOutController(FormFactory formFactory, AsyncCacheApi asyncCacheApi, MessagesApi messagesApi) {
        this.dynamicForm = formFactory.form();
        this.asyncCacheApi = asyncCacheApi;
        this.messagesApi = messagesApi;
    }

    public Result index() {
        return Results.ok(views.html.signout.index.render());
    }

    @Transactional
    public Result deleteUser() {
        DynamicForm requestData = dynamicForm.bindFromRequest();

        // null check の必要あるかも
        Long id = Long.valueOf(session().get("userID"));
        User user = userService.findById(id);

        // TODO キャッシュしたUUIDとユーザーIDの照合 or password認証

        if (user == null) {
            Logger.warn(messagesApi.get(Lang.apply(Lang.defaultLang().code()), "client.errors.400"));
            return Results.badRequest(views.html.signup.top.render());
        }

        userService.deleteUser(user);
        session().remove("userID");

        flash("userDeleted", messagesApi.get(Lang.apply(Lang.defaultLang().code()),"signout.status.204"));

        return redirect("/");
    }

}
