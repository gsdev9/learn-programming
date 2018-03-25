package controllers;

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
    public AccountController(FormFactory formFactory) {
        form = formFactory.form(User.class);
    }

    @Transactional
    public Result AccountDetail() {
        User user = userService.find("かわの");
        form = form.fill(user);
        Logger.info("ここ:" + form.value().get().name);

        return Results.ok(views.html.user.userUpdate.render(form));
    }

    @Transactional
    public Result formresult() {
        Form<User> result = form.bindFromRequest();
        Logger.info("name:" + result.get().name);
        Logger.info("userId:" + result.get().userId);
        userService.updateUser(result.get());
        return Results.redirect("/useredit");
    }

}
