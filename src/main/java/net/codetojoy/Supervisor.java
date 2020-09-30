package net.codetojoy;

import akka.actor.typed.*;
import akka.actor.typed.javadsl.*;

import java.util.*;
import java.util.stream.Stream;

import net.codetojoy.message.*;
import net.codetojoy.util.Timer;

public class Supervisor extends AbstractBehavior<Supervisor.Command> {
    private static Range range;

    public static Behavior<Supervisor.Command> create(Range range) {
        Supervisor.range = range;
        return Behaviors.setup(Supervisor::new);
    }

    private Supervisor(ActorContext<Supervisor.Command> context) {
        super(context);
    }

    public sealed interface Command permits BeginCommand {} // , BeginAckEvent {}

    public static final class BeginCommand implements Command {
        final long requestId;
        // final ActorRef<BeginAckEvent> replyTo;

        public BeginCommand(long requestId) { // , ActorRef<BeginAckEvent> replyTo) {
            this.requestId = requestId;
            // this.replyTo = replyTo;
        }
    }

    /*
    public static final class BeginAckEvent implements Command {
        final long requestId;
        public BeginAckEvent(long requestId) {
            this.requestId = requestId;
        }
    }
    */

    @Override
    public Receive<Supervisor.Command> createReceive() {
        return newReceiveBuilder()
                   .onMessage(BeginCommand.class, this::onBeginCommand)
                   .onSignal(PostStop.class, signal -> onPostStop())
                   .build();
    }

    private Behavior<Supervisor.Command> onBeginCommand(BeginCommand command) {
        try {
            var timer = new Timer();
            // create calculator
            ActorRef<CalcCommand> calculator = getContext().spawn(Calculator.create(), "calculator");

            // create reporter
            ActorRef<CalcEvent> reporter = getContext().spawn(Reporter.create(), "reporter");

            // create workers
            ActorRef<ProcessRangeCommand> worker = getContext().spawn(Worker.create(), "workerN");

            // assign blocks to workers
            var processRangeCommand = new ProcessRangeCommand(Supervisor.range, calculator, reporter);
            worker.tell(processRangeCommand);

            getContext().getLog().info("TRACER Supervisor {}", timer.getElapsed("onBeginCommand"));

            // example of response
            // command.replyTo.tell(new BeginAckEvent(command.requestId));
        } catch (Exception ex) {
            getContext().getLog().error("TRACER Supervisor caught exception! ex: {}", ex.getMessage());
        }

        return this;
    }

    private Behavior<Command> onPostStop() {
         getContext().getLog().info("TRACER supervisor STOPPED");
         return Behaviors.stopped();
     }
}
