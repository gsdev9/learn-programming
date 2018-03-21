package execptions;

import org.pac4j.core.context.*;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import play.mvc.Result;

import static play.mvc.Controller.flash;
import static play.mvc.Results.redirect;

/**
 * ログインエラーのハンドリング
 *
 * @author arapiku
 */
public class LoginHttpActionAdapter extends DefaultHttpActionAdapter {

    @Override
    public Result adapt(int status, PlayWebContext ctx) {
        if (status == HttpConstants.UNAUTHORIZED) {
            ctx.getJavaContext().session().remove(Pac4jConstants.SESSION_ID);
            flash("failed", "login.errors.401");
            return redirect("/");
        } else if (status == HttpConstants.FORBIDDEN) {
            flash("failed", "login.errors.403");
            return redirect("/");
        } else {
            return super.adapt(status, ctx);
        }
    }

}
