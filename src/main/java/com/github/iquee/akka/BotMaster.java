package com.github.iquee.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;

public class BotMaster extends AbstractActor {

    public BotMaster() {
        for (int index = 0; index < 10; index++) {
            ActorRef actorRef = getContext().actorOf(Props.create(AkkaBot.class));
            System.out.println("Creating: " + actorRef.path());
            getContext().watch(actorRef);
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StartChildBots.class, this::onStartChildsBots)
                .match(Stop.class, this::onStopChildsBot)
                //.match(Terminated.class, this::onChildTerminated)
                .build();
    }

    public void onStartChildsBots(StartChildBots startChildBots){
        final Move move = new Move(Direction.FORWARD);
        for (ActorRef child : getContext().getChildren()) {
            System.out.println("Master started moving " + child);
            child.tell(move, getSelf());
        }
    }

    public void onChildTerminated(Terminated terminated){
        System.out.println("Child has stopped, starting a new one");
        final ActorRef actorRef = getContext().actorOf(Props.create(AkkaBot.class));
        getContext().watch(actorRef);
    }

    public void onStopChildsBot(Stop stop){
        for (ActorRef child : getContext().getChildren()){
            child.tell(stop, child);
        }
    }
}

class StartChildBots{}
