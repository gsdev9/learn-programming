package controllers;

import com.google.inject.Inject;
import forms.ReviewForm;
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
        //既存レビューがあるか
        if (reviewService.findByPurchasedId(purchasedTicketId).isEmpty()) {
            String userId = Controller.session().get("userID");
            PurchasedTicket purchasedTicket = purchasedTicketService.findById(purchasedTicketId);
            Logger.info("sessin:{} , DB:{}", userId, purchasedTicket.getBuyer().getUserId());
            //チケット購入者のUserIdと突合
            if (Long.valueOf(userId).equals(purchasedTicket.getBuyer().getUserId())) {
                ReviewForm reviewForm = new ReviewForm();
                reviewForm.setPurchasedTicketId(purchasedTicket.getPurchasedTicketId());
                Form<ReviewForm> f = formFactory.form(ReviewForm.class).fill(reviewForm);
                if (f.hasErrors()) {
                    Controller.flash("badRequest", "fromに不正な値が含まれています");
                    return Results.redirect("/top");
                }
                return Results.ok(views.html.review.index.render(f));
            } else {
                Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400", "userId: " + userId));
                Controller.flash("badRequest", "不正なアクセスです");
                return Results.redirect("/top");
            }
        }
        Controller.flash("badRequest", "レビュー済みです");
        return Results.redirect("/top");
    }

    @Transactional
    public Result reviewCreate() {
        Form<ReviewForm> f = formFactory.form(ReviewForm.class).bindFromRequest();
        Long purchasedTicketId = f.get().getPurchasedTicketId();
        PurchasedTicket purchasedTicket = purchasedTicketService.findById(purchasedTicketId);
        UserReview userReview = new UserReview();
        userReview.setComment(f.get().getComment());
        userReview.setEvaluation(f.get().getScore());
        userReview.setBuyer(purchasedTicket.getBuyer());
        userReview.setPurchasedTicketId(purchasedTicket.getPurchasedTicketId());
        userReview.setTicket(purchasedTicket.getTicket());
        reviewService.registUserReview(userReview);
        Controller.flash("updated", "レビューを送信しました。");
        return Results.redirect("/top");
    }

}
