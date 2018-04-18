package controllers;

import Actors.ChatRequestActor;
import Actors.ChatResponseActor;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.WebSocket;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WebSocketController extends Controller {

    private final ActorSystem actorSystem;
    private final Materializer materializer;
    @Inject
    private RoomClient roomClient;

    @Inject
    public WebSocketController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

//    public WebSocket socket() {
//        return WebSocket.Text.accept(request ->
//                ActorFlow.actorRef(MyWebSocketActor::props, actorSystem, materializer)
//        );
//    }

    public WebSocket socket() {
        return WebSocket.Text.accept(request -> {
            roomClient.createbus();
            Flow in = ActorFlow.actorRef(ChatRequestActor::props, actorSystem, materializer);
            Flow out = ActorFlow.actorRef(ChatResponseActor::props, actorSystem, materializer);
            Flow broad = in.viaMat(roomClient.bus, Keep.right()).viaMat(out, Keep.right());
            return broad;
//            return in.viaMat(roomClient.bus, Keep.both());
        });
    }
}
