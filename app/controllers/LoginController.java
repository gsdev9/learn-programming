package controllers;

import org.pac4j.core.profile.*;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.Secure;
import org.pac4j.play.store.PlaySessionStore;
import play.cache.AsyncCacheApi;
import play.mvc.*;

import javax.inject.Inject;
import java.util.*;

/**
 * ログイン処理
 *
 * @author arapiku
 */
public class LoginController extends Controller {

    private final AsyncCacheApi cacheApi;

    @Inject
    protected PlaySessionStore playSessionStore;

    @Inject
    public LoginController(AsyncCacheApi cacheApi) {
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
     * ログイン後ページの表示
     *
     * @return
     */
    @Secure(clients = "TwitterClient")
    public Result login() {
        // UUIDの発行
        String accessToken = UUID.randomUUID().toString();
        // テスト用に15分にしているので後で変える＆confに移すべき
        cacheApi.set(accessToken, accessToken, 60 * 15);
        return Results.ok(views.html.top.render(getProfiles(), accessToken));
    }

}
