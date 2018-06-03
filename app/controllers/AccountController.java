package controllers;

import controllers.constants.AccountConstant;
import controllers.constants.FileUploadConstant;
import models.PurchasedTicket;
import models.Ticket;
import models.User;
import models.UserReview;
import play.Logger;
import play.api.i18n.Lang;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import services.PurchasedTicketService;
import services.ReviewService;
import services.TicketService;
import services.UserService;

import javax.inject.Inject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class AccountController extends Controller {

    private static final String THUMBNAIL_DEFAULT_PATH = "uploadfiles/thumbnail/";
    private final MessagesApi messagesApi;
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
    private AccountConstant accountConstant;

    @Inject
    public AccountController(FormFactory formFactory, MessagesApi messagesApi) {
        form = formFactory.form(User.class);
        this.messagesApi = messagesApi;
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
        //thumbnailFile処理
        String thumbnailRoutePath;
        String encodeFileName;
        Http.MultipartFormData<File> body = Controller.request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> thumbnailFile = body.getFile("thumbnailPath");
        if (thumbnailFile != null) {
            String fileName = thumbnailFile.getFilename();
            File tmpFile = thumbnailFile.getFile();
            thumbnailRoutePath = AccountController.THUMBNAIL_DEFAULT_PATH + fileName;
            File file = new File(System.getProperty("user.dir") + "/public/" + thumbnailRoutePath);
            tmpFile.renameTo(file);
            try {
                encodeFileName = URLEncoder.encode(fileName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Controller.flash("error", FileUploadConstant.FAILDEODE);
                return Results.badRequest();
            }
        } else {
            Controller.flash("error", FileUploadConstant.FILENOTFOUND);
            return Results.badRequest();
        }
        //TODO:URIからパス表示に変更修正を入れる
        String thumbnailUriHeader = "http://";
        //SSL判定
        if (Controller.request().secure()) {
            thumbnailUriHeader = "https://";
        }
        String thumbnailUri = thumbnailUriHeader + Controller.request().host() + "/thumbnailview?fileName=" + encodeFileName;
        //form処理
        //TODO sessionの中にcookie(userID)が存在するかの判定がいる
        Form<User> result = form.bindFromRequest();
        User newUser = result.get();
        String userID = Controller.session().get("userID");
        newUser.setUserId(Long.parseLong(userID));
        newUser.setThumbnailPath(thumbnailUri);
        userService.updateUser(newUser);
        Controller.flash("result", accountConstant.UPDATE_SUCCESS);
        return Results.redirect("/top");
    }

    /**
     * ユーザー情報画面の出力
     *
     * @return
     */
    @Transactional
    public Result UserRefDetail(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            Logger.warn(messagesApi.get(Lang.defaultLang(), "client.errors.400", "userId: " + userId));
            return Results.notFound(views.html.notfound.index.render());
        }
        List<Ticket> myTickets = ticketService.findByUser(user);
        List<UserReview> UserReviews = reviewService.findByUserId(userId);
        return Results.ok(views.html.user.userRefDetail.render(user, myTickets, UserReviews));
    }


}
