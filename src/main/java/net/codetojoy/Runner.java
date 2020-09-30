package net.codetojoy;

import akka.actor.typed.ActorSystem;
import java.io.*;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        int low = 2;
        int high = 10;
        var range = new Range(low, high);
        ActorSystem<Supervisor.Command> supervisor = ActorSystem.create(Supervisor.create(range), "supervisor");
        long requestId = 5150;
        supervisor.tell(new Supervisor.BeginCommand(requestId));

        try {
            promptForUserInput();
        } catch (Exception ignored) {
        } finally {
            supervisor.terminate();
        }
    }

    static void promptForUserInput() {
        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (Exception ex) {
        }
    }
}
