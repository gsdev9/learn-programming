package Actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import play.Logger;

public class ChatResponseActor extends UntypedAbstractActor {

    private static ActorRef out;


    public ChatResponseActor(ActorRef out) {
        this.out = out;
    }

    public static Props props(ActorRef out) {
        return Props.create(ChatResponseActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            Logger.info("acrtorlog:ChatResponseActor");
//            ChatResponseActor.out.forward(message.toString(), getContext());
            ChatResponseActor.out.tell(message, self());
        }
        {
            //actor-stop
            //特定時間が経過後タイムアウトの処理が必要
//            self().tell(PoisonPill.getInstance(), self());
        }
    }
}
