package controllers;

import com.google.inject.Inject;
import forms.ReviewFrom;
import models.PurchasedTicket;
import models.UserReview;
import play.Logger;
import play.api.i18n.Lang;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.PurchasedTicketService;
import services.ReviewService;

public class ReviewController extends Controller {

    private final FormFactory formFactory;

    private final MessagesApi messagesApi;

    @Inject
    private ReviewService reviewService;

    @Inject
    private PurchasedTicketService purchasedTicketService;

    @Inject
    public ReviewController(FormFactory formFactory, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
    }

    @Transactional
    public Result reviewRoute(Long purchasedTicketId) {
        String userId = Controller.session().get("userID");
        PurchasedTicket purchasedTicket = purchasedTicketService.findById(purchasedTicketId);
        Logger.info("sessin:{} , DB:{}", userId, purchasedTicket.getBuyer().getUserId());
        //チケット購入者のUserIdと突合
        if (Long.valueOf(userId).equals(purchasedTicket.getBuyer().getUserId())) {
            ReviewFrom reviewFrom = new ReviewFrom();
            reviewFrom.setPurchasedTicketId(purchasedTicketId);
            Form<ReviewFrom> f = formFactory.form(ReviewFrom.class).fill(reviewFrom);
            return Results.ok(views.html.review.index.render(f));
        } else {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400", "userId: " + userId));
            Controller.flash("badRequest", "不正なアクセスです");
            return Results.redirect("/top");
        }
    }

    @Transactional
    public Result reviewCreate() {
        Form<ReviewFrom> f = formFactory.form(ReviewFrom.class).bindFromRequest();
        Long purchasedTicketId = f.get().getPurchasedTicketId();
        PurchasedTicket purchasedTicket = purchasedTicketService.findById(purchasedTicketId);
        UserReview userReview = new UserReview();
        userReview.setComment(f.get().getComment());
        userReview.setEvaluation(f.get().getScore());
        userReview.setBuyer(purchasedTicket.getBuyer());
        userReview.setTicket(purchasedTicket.getTicket());
        reviewService.registUserReview(userReview);
        Controller.flash("updated", "レビューを送信しました。");
        return Results.redirect("/top");
    }

}
