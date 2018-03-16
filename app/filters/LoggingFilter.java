package filters;

import akka.util.ByteString;
import play.Logger;
import play.libs.streams.Accumulator;
import play.mvc.*;

import javax.inject.Inject;
import java.util.concurrent.Executor;

public class LoggingFilter extends EssentialFilter {

    private final Executor executor;

    @Inject
    public LoggingFilter(Executor executor) {
        this.executor = executor;
    }

    @Override
    public EssentialAction apply(EssentialAction next) {
        return EssentialAction.of(request -> {
            long startTime = System.currentTimeMillis();
            Accumulator<ByteString, Result> accumulator = next.apply(request);
            return accumulator.map(result -> {
                long endTime = System.currentTimeMillis();
                long requestTime = endTime - startTime;
                Logger.info("{} {} {} took {}ms and returned {}",
                    request.method(),
                    request.uri(),
                    request.getHeaders(),
                    requestTime,
                    result.status());
                return result.withHeader("Request-Time", "" + requestTime);
            }, executor);
        });
    }
}
