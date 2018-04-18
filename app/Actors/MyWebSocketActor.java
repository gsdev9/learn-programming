package Actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class MyWebSocketActor extends UntypedAbstractActor {

    private static ActorRef out;


    public MyWebSocketActor(ActorRef out) {
        this.out = out;
    }

    public static Props props(ActorRef out) {
        return Props.create(MyWebSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            MyWebSocketActor.out.tell(message, self());
        }
        {
            //actor-stop
            //特定時間が経過後タイムアウトの処理が必要
//            self().tell(PoisonPill.getInstance(), self());
        }
    }

}