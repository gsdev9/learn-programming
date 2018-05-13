package modules;

import com.google.inject.AbstractModule;
import execptions.LoginHttpActionAdapter;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.TwitterClient;
import org.pac4j.play.*;
import org.pac4j.play.store.*;
import play.Environment;

import javax.inject.Inject;

/**
 * Pac4j利用のためのモジュール
 *
 * @author arapiku
 */
public class Pac4jModule extends AbstractModule {

    private static final String CONSUMER_KEY = "oauth.consumerKey";

    private static final String CONSUMER_SECRET = "oauth.consumerSecret";

    private static final String CALL_BACK_URL = "oauth.callBackUrl";

    private final com.typesafe.config.Config config;

    @Inject
    public Pac4jModule(Environment environment, com.typesafe.config.Config config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(PlaySessionStore.class).to(PlayCacheSessionStore.class);

        // Twitter API
        TwitterClient twitterClient = new TwitterClient(
            config.getString(CONSUMER_KEY),
            config.getString(CONSUMER_SECRET)
        );

        // Facebook API
//        FacebookClient facebookClient = new FacebookClient(
//              "143847239778871",
//              "99fd2f94f37a128ea2cc232c2b5f220e"
//        );


        // コールバックURL、クライアント
        Clients clients = new Clients(config.getString(CALL_BACK_URL), twitterClient);

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
