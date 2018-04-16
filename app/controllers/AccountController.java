package controllers;

import controllers.constants.AccountConstants;
import models.*;
import play.data.*;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.*;

import javax.inject.Inject;
import java.util.List;

public class AccountController extends Controller {

    private Form<User> form;

    @Inject
    private UserService userService;

    @Inject
    TicketService ticketService;

    @Inject
    PurchasedTicketService purchasedTicketService;

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
        Long userID = Long.valueOf(Controller.session().get("userID"));
        User user = userService.findById(userID);
        form = form.fill(user);

        List<PurchasedTicket> purchasedTickets = purchasedTicketService.findByUserId(userID);

        List<Ticket> myTickets = ticketService.findByUser(user);

        return Results.ok(views.html.user.userUpdate.render(form, myTickets, purchasedTickets));
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
        return Results.redirect("/index");
    }

}
