package models;

import akka.stream.UniqueKillSwitch;
import akka.stream.javadsl.Flow;

public class RoomBus {

    public Flow<String, String, UniqueKillSwitch> bus;

}
