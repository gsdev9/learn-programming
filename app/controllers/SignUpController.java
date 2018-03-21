package controllers;

import dtos.UserDTO;
import models.User;
import org.pac4j.core.profile.*;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.Secure;
import org.pac4j.play.store.PlaySessionStore;
import play.cache.AsyncCacheApi;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.UserService;

import javax.inject.Inject;
import java.util.*;

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

    private final AsyncCacheApi cacheApi;

    @Inject
    protected PlaySessionStore playSessionStore;

    @Inject
    public SignUpController(AsyncCacheApi cacheApi) {
        this.cacheApi = cacheApi;
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
        return Results.ok(views.html.signup.render());
    }

    /**
     * 登録処理
     *
     * @return
     */
    @Secure(clients = "TwitterClient")
    public Result signUp() {
        // UUIDの発行
        String accessToken = UUID.randomUUID().toString();
        cacheApi.set(accessToken, accessToken, 60 * 15);

        String name = getProfiles().get(0).getDisplayName();
        String email = getProfiles().get(0).getEmail();
        String thumbnailPath = String.valueOf(getProfiles().get(0).getPictureUrl());

        User userData = userDTO.duplicateUserInfo(accessToken, name, email, thumbnailPath);
        userService.registUser(userData);

        return redirect("/top");
    }

    /**
     * 登録後のリダイレクトページ
     *
     * @return
     */
    public Result top() {
        return Results.ok(views.html.top.render(getProfiles()));
    }

}
