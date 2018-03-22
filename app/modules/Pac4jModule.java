package modules;

import com.google.inject.AbstractModule;
import execptions.LoginHttpActionAdapter;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.*;
import org.pac4j.play.*;
import org.pac4j.play.store.*;

/**
 * Pac4j利用のためのモジュール
 *
 * @author arapiku
 */
public class Pac4jModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlaySessionStore.class).to(PlayCacheSessionStore.class);

        // Twitter API
        TwitterClient twitterClient = new TwitterClient(
            "d7ZRfaHu4aD5A1vRo0sVzINnN",
            "GDEjkITvPTm29ymnkaqwRpiji5dgZZjrJUk9d7adeZvjLZwX7r"
        );

        // Facebook API
//        FacebookClient facebookClient = new FacebookClient(
//              "143847239778871",
//              "99fd2f94f37a128ea2cc232c2b5f220e"
//        );


        // コールバックURL、クライアント
        Clients clients = new Clients("http://localhost:9000/callback", twitterClient);

        // config
        Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        config.setHttpActionAdapter(new LoginHttpActionAdapter());
        bind(Config.class).toInstance(config);

        // callback
        CallbackController callbackController = new CallbackController();
        callbackController.setDefaultUrl("/");
        bind(CallbackController.class).toInstance(callbackController);

        // logout
        LogoutController logoutController = new LogoutController();
        logoutController.setDefaultUrl("/");
        logoutController.setDestroySession(true);
        bind(LogoutController.class).toInstance(logoutController);

    }

}
