package net.codetojoy;

import akka.actor.typed.*;
import akka.actor.typed.javadsl.*;

import net.codetojoy.message.*;

public class Calculator extends AbstractBehavior<Calculator.Command> {

    public static Behavior<Calculator.Command> create() {
        return Behaviors.setup(Calculator::new);
    }

    private Calculator(ActorContext<Calculator.Command> context) {
        super(context);
    }

    public sealed interface Command permits CalcCommand {}

    public static final class CalcCommand implements Command {
        final long requestId;
        final int a;
        final int b;
        final int c;
        final ActorRef<CalcEvent> replyTo;

        public CalcCommand(long requestId, int a, int b, int c, ActorRef<CalcEvent> replyTo) {
            this.requestId = requestId;
            this.a = a;
            this.b = b;
            this.c = c;
            this.replyTo = replyTo;
        }

        public String toString() {
            final String format = "a: %d b: %d c: %d";
            return String.format(format, a, b, c);
        }
    }

    @Override
    public Receive<Calculator.Command> createReceive() {
        return newReceiveBuilder()
                   .onMessage(CalcCommand.class, this::onCalcCommand)
                   .onSignal(PostStop.class, signal -> onPostStop())
                   .build();
    }

    private Behavior<Calculator.Command> onCalcCommand(CalcCommand calcCommand) {
        int a = calcCommand.a;
        int b = calcCommand.b;
        int c = calcCommand.c;

        if (c == (a + b)) {
            getContext().getLog().info("TRACER Calculator match: {}", calcCommand.toString());
            boolean result = true;
            var calcEvent = new CalcEvent(a, b, c, result);
            calcCommand.replyTo.tell(calcEvent);
        }

        return this;
    }

    private Behavior<Command> onPostStop() {
         getContext().getLog().info("TRACER calculator STOPPED");
         return Behaviors.stopped();
    }
}
