package com.github.iquee.akka;

import akka.actor.AbstractActor;

import java.util.Optional;

public class AkkaBot extends AbstractActor {
    private Optional<Direction> direction = Optional.empty();
    private boolean moving = false;

    public Receive createReceive() {
        return receiveBuilder()
                .match(Move.class, this::onMove)
                .match(Stop.class, this::onStop)
                .build();
    }

    private void onMove(Move move){
        moving = true;
        direction = Optional.of(move.direction);
        System.out.println(getSelf().path() +  ": I'm now moving to " + direction.get());
    }

    private void onStop(Stop stop) {
        moving = false;
        System.out.println(getSelf().path() + ": I stopped moving");
    }

}

enum Direction {FORWARD, BACKWARDS, LEFT, RIGHT}

class Move{
    public final Direction direction;

    public Move(Direction direction) {
        this.direction = direction;
    }
}

class Stop{}
