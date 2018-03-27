package controllers;

import constants.AccountConstans;
import models.User;
import play.Logger;
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
    private AccountConstans accountConstans;

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
    public Result AccountDetail() {
        //usernameをどこから引っ張ってくるの決めないといけない
        User user = userService.findByUserName("__poyo_poyo__");
        form = form.fill(user);
        Logger.info("ここ:" + form.value().get().userName);

        return Results.ok(views.html.user.userUpdate.render(form));
    }

    /**
     * ユーザー情報の変更を反映
     *
     * @return
     */
    @Transactional
    public Result formresult() {
        Form<User> result = form.bindFromRequest();
        Logger.info("name:" + result.get().userName);
        Logger.info("userId:" + result.get().userId);
        userService.updateUserDetail(result.get());
        Controller.flash("result", accountConstans.UPDATE_SUCCESS);
        return Results.redirect("/userdetail");
    }

}
