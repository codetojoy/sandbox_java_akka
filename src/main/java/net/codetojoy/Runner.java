package net.codetojoy;

import akka.actor.typed.ActorSystem;
import java.io.*;
import java.util.List;

import net.codetojoy.message.*;

public class Runner {
    public static void main(String[] args) {
        int low = 2;
        int high = 20;
        Range range = new Range(low, high);
        ActorSystem<BeginProcessing> supervisor = ActorSystem.create(Supervisor.create(range), "supervisor");
        supervisor.tell(new BeginProcessing("factorial"));

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
