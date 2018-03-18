package execptions;

import play.Logger;
import play.http.HttpErrorHandler;
import play.i18n.*;
import play.mvc.*;

import javax.inject.*;
import java.util.concurrent.*;

import static play.api.i18n.Lang.apply;

@Singleton
public class ErrorHandler implements HttpErrorHandler {

    private final MessagesApi messagesApi;

    @Inject
    public ErrorHandler(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Override
    public CompletionStage<Result> onClientError(Http.RequestHeader request, int statusCode, String message) {
        String errorMsg = messagesApi.get(apply(Lang.defaultLang().code()), "client.errors." + statusCode, message);
        return CompletableFuture.completedFuture(Results.status(statusCode, errorMsg));
    }

    @Override
    public CompletionStage<Result> onServerError(Http.RequestHeader request, Throwable exception) {
        Logger.error(exception.getMessage(), exception);
        return CompletableFuture.completedFuture(Results.internalServerError("サーバーエラーが発生しました。", exception.getMessage()));
    }
    
}
