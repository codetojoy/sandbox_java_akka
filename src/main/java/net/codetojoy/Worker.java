package net.codetojoy;

import akka.actor.typed.*;
import akka.actor.typed.javadsl.*;

public class Worker extends AbstractBehavior<Worker.Command> {
    public static Behavior<Worker.Command> create() {
        return Behaviors.setup(Worker::new);
    }

    private Worker(ActorContext<Worker.Command> context) {
        super(context);
    }

    public sealed interface Command permits ProcessRangeCommand {}

    public static final class ProcessRangeCommand implements Command {
        final long requestId;
        final Range range;
        final ActorRef<Calculator.Command> calculator;
        final ActorRef<Reporter.Command> reporter;
        // final ActorRef<Worker.ProcessRangeAckEvent> replyTo;

        public ProcessRangeCommand(long requestId, Range range,
                                   ActorRef<Calculator.Command> calculator, ActorRef<Reporter.Command> reporter) {
            this.requestId = requestId;
            this.range = range;
            this.calculator = calculator;
            this.reporter = reporter;
            // this.replyTo = replyTo;
        }
    }

    /*
    public static final class ProcessRangeAckEvent implements Command {
        final long requestId;

        public ProcessRangeAckEvent(long requestId) {
            this.requestId = requestId;
        }
    }
    */

    @Override
    public Receive<Worker.Command> createReceive() {
        return newReceiveBuilder()
                   .onMessage(ProcessRangeCommand.class, this::onProcessRangeCommand)
                   .onSignal(PostStop.class, signal -> onPostStop())
                   .build();
    }

    private Behavior<Worker.Command> onProcessRangeCommand(ProcessRangeCommand command) {

        var requestId = 1L;
        var range = command.range;

        for (int a = range.low; a <= range.high; a++) {
            for (int b = range.low; b <= range.high; b++) {
                for (int c = range.low; c <= range.high; c++) {
                    var calcCommand = new Calculator.CalcCommand(requestId, a, b, c, command.reporter);
                    command.calculator.tell(calcCommand);
                    requestId++;
                }
            }
        }

        // TODO
        // example of response
        // command.replyTo.tell(new ProcessRangeAckEvent(command.requestId));

        return this;
    }

    private Behavior<Command> onPostStop() {
         getContext().getLog().info("TRACER worker STOPPED");
         return Behaviors.stopped();
    }
}
