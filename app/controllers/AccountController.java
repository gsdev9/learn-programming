package controllers;

import controllers.constants.AccountConstants;
import models.PurchasedTicket;
import models.Ticket;
import models.User;
import models.UserReview;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.PurchasedTicketService;
import services.ReviewService;
import services.TicketService;
import services.UserService;

import javax.inject.Inject;
import java.util.List;

public class AccountController extends Controller {

    private Form<User> form;

    @Inject
    private UserService userService;

    @Inject
    private TicketService ticketService;

    @Inject
    private PurchasedTicketService purchasedTicketService;

    @Inject
    private ReviewService reviewService;

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
        userService.updateUser(newUser);
        Controller.flash("result", accountConstants.UPDATE_SUCCESS);
        return Results.redirect("/index");
    }

    /**
     * ユーザー情報画面の出力
     *
     * @return
     */
    @Transactional
    public Result UserRefDetail(Long userId) {
        User user = userService.findById(userId);
        List<Ticket> myTickets = ticketService.findByUser(user);
        List<UserReview> UserReviews = reviewService.findByUserId(userId);
        return Results.ok(views.html.user.userRefDetail.render(user, myTickets, UserReviews));
    }


}
