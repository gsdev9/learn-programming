package controllers;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.japi.Pair;
import akka.stream.KillSwitches;
import akka.stream.Materializer;
import akka.stream.UniqueKillSwitch;
import akka.stream.javadsl.*;
import models.RoomPool;
import play.Logger;
import scala.concurrent.duration.FiniteDuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;

@Singleton
public class RoomClient extends Thread {

    private final ActorSystem actorSystem;
    private final Materializer materializer;
    private final AtomicReferenceArray atomicReferenceArray;
    public Flow<String, String, UniqueKillSwitch> bus;

    @Inject
    public RoomClient(Materializer materializer, ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        atomicReferenceArray = new AtomicReferenceArray(100);
    }

    public synchronized void roomCreate(String roomId) {
        RoomPool roomPool = new RoomPool();
        roomPool.roomId = roomId;
        for (int i = 1; i <= atomicReferenceArray.length() - 1; i++) {
            if (!atomicReferenceArray.get(i).equals(roomPool)) {
                atomicReferenceArray.set(i, roomPool);
            }
        }
    }

    public void createbus() {

        Pair<Sink<String, NotUsed>, Source<String, NotUsed>> sinkAndSource =
                MergeHub.of(String.class, 16)
                        .toMat(BroadcastHub.of(String.class, 256), Keep.both())
                        .run(materializer);
        Sink<String, NotUsed> sink = sinkAndSource.first();
        Source<String, NotUsed> source = sinkAndSource.second();

        source.runWith(Sink.ignore(), materializer);
        Flow<String, String, UniqueKillSwitch> busFlow =
                Flow.fromSinkAndSource(sink, source)
                        .joinMat(KillSwitches.singleBidi(), Keep.right()).backpressureTimeout(FiniteDuration.create(1, TimeUnit.SECONDS));
        Logger.info("bussflow make");
        bus = busFlow;
    }


}
