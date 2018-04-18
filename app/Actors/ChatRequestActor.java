package Actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import play.Logger;

public class ChatRequestActor extends UntypedAbstractActor {

    private static ActorRef out;
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public ChatRequestActor(ActorRef out) {
        this.out = out;
    }

    public static Props props(ActorRef out) {
        return Props.create(ChatRequestActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            Logger.info("akkalog:ChatRequestActor");
            ChatRequestActor.out.forward(message.toString(), getContext());
        }
        {
            //actor-stop
            //特定時間が経過後タイムアウトの処理が必要
//            self().tell(PoisonPill.getInstance(), self());
        }
    }

    @Override
    public void postStop() {
    }
}
