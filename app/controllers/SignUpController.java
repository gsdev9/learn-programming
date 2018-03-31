package controllers;

import dtos.UserDTO;
import models.User;
import org.pac4j.core.profile.*;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.Secure;
import org.pac4j.play.store.PlaySessionStore;
import play.api.i18n.Lang;
import play.data.*;
import play.db.jpa.Transactional;
import play.i18n.MessagesApi;
import play.mvc.*;
import services.UserService;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * ログイン処理
 *
 * @author arapiku
 */
@Transactional
public class SignUpController extends Controller {

    @Inject
    private UserService userService;

    @Inject
    private UserDTO userDTO;

    private final FormFactory formFactory;

    private final MessagesApi messagesApi;

    @Inject
    protected PlaySessionStore playSessionStore;

    @Inject
    public SignUpController(FormFactory formFactory, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
    }

    /**
     * プロフィール情報の取得
     *
     * @return
     */
    private List<CommonProfile> getProfiles() {
        PlayWebContext ctx = new PlayWebContext(Controller.ctx(), playSessionStore);
        ProfileManager<CommonProfile> profileManager = new ProfileManager<>(ctx);
        return profileManager.getAll(true);
    }

    /**
     * 新規会員登録ページの表示
     *
     * @return
     */
    public Result index() {
        return Results.ok(views.html.signup.index.render());
    }

    /**
     * Twitterから情報取得
     *
     * @return
     */
    @Secure(clients = "TwitterClient")
    public Result accessTwitter() {

        /** 今後対応ここから（現在使っておらず） */
        // UUIDの発行
        // String sessionId = UUID.randomUUID().toString();
        // String uuid = UUID.randomUUID().toString();

        // session("sessionId", sessionId);
        // session("UUID", uuid);
        // 現時点でキャッシュ使ってる意味なし、暫定対応
        // asyncCacheApi.set(sessionId, uuid, 60 * 60 * 24 * 180);
        /** 今後対応ここまで */

        String nickName = getProfiles().get(0).getDisplayName();
        String userName = getProfiles().get(0).getUsername();
        String email = getProfiles().get(0).getEmail();
        String thumbnailPath = String.valueOf(getProfiles().get(0).getPictureUrl());

//        User userData = userDTO.duplicateUserInfo(userName, displayName, email, thumbnailPath);
//        userService.registUser(userData);

        Form<User> f = formFactory.form(User.class);

        User user = new User();
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setEmail(email);
        user.setThumbnailPath(thumbnailPath);
        f = f.fill(user);
        return Results.ok(views.html.signup.input.render(f));
    }

    /**
     * ユーザー情報の入力 -> 登録
     *
     * @return
     */
    public Result input() {

        Form<User> f = formFactory.form(User.class).bindFromRequest();
        String userName = f.get().userName;
        String nickName = f.get().nickName;
        String email = f.get().email;
        String password = f.get().password;
        String thumbnailPath = f.get().thumbnailPath;

        User user = userDTO.duplicateUserInfo(userName, nickName, email, password, thumbnailPath);

        try {
            userService.registUser(user);
        } catch (PersistenceException pe) {
            flash("unique_error", messagesApi.get(Lang.apply(Lang.defaultLang().code()),"signup.errors.400.unique"));
            return badRequest(views.html.signup.input.render(f));
        }

        flash("success", messagesApi.get(Lang.apply(Lang.defaultLang().code()),"signup.status.200"));
        session("userID", String.valueOf(user.userId));
        return redirect("/top");
    }

    /**
     * 登録後のリダイレクトページ
     *
     * @return
     */
    public Result top() {
        return Results.ok(views.html.signup.top.render());
    }

}
