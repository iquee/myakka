package br.com.lhktaira.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Application {

    public static void main(String[] args) throws Exception{
        final ActorSystem actorSystem = ActorSystem.create();

        final ActorRef botMaster = actorSystem.actorOf(
                Props.create(BotMaster.class),
                "botMaster"
                //Props.create(AkkaBot.class),
                //"akkaBot"
        );

        botMaster.tell(
                new StartChildBots(),
                //new Move(Direction.FORWARD),
                ActorRef.noSender());

        /*
        botMaster.tell(
                new Move(Direction.BACKWARDS),
                ActorRef.noSender());
                */

        botMaster.tell(
                new Stop(),
                ActorRef.noSender());

        Thread.sleep(5000);
        System.out.println("Press any key to terminate");
        System.in.read();
        System.out.println("Shutting down actor system...");
        actorSystem.terminate();
    }
}
