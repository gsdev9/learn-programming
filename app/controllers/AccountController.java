package controllers;

import controllers.constants.AccountConstants;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.UserService;

import javax.inject.Inject;

public class AccountController extends Controller {

    private Form<User> form;

    @Inject
    private UserService userService;

    @Inject
    private AccountConstants accountConstants;

    @Inject
    public AccountController(FormFactory formFactory) {
        form = formFactory.form(User.class);
    }

    /**
     * ユーザー情報画面の出力
     *
     * @return
     */
    @Transactional
    public Result UserDetail() {
        //TODO sessionの中にcookie(userID)が存在するかの判定がいる
        String userID = Controller.session().get("userID");
        User user = userService.findById(Long.parseLong(userID));
        form = form.fill(user);
        return Results.ok(views.html.user.userUpdate.render(form));
    }

    /**
     * ユーザー情報の変更を反映
     *
     * @return
     */
    @Transactional
    public Result UserUpdate() {
        //TODO sessionの中にcookie(userID)が存在するかの判定がいる
        Form<User> result = form.bindFromRequest();
        User newUser = result.get();
        String userID = Controller.session().get("userID");
        newUser.setUserId(Long.parseLong(userID));
        userService.updateUserDetail(newUser);
        Controller.flash("result", accountConstants.UPDATE_SUCCESS);
        return Results.redirect("/top");
    }

}
