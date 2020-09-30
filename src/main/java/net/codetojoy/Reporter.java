package net.codetojoy;

import akka.actor.typed.*;
import akka.actor.typed.javadsl.*;

public class Reporter extends AbstractBehavior<Reporter.Command> {
    public static Behavior<Reporter.Command> create() {
        return Behaviors.setup(Reporter::new);
    }

    private Reporter(ActorContext<Reporter.Command> context) {
        super(context);
    }

    public sealed interface Command permits CalcEvent {}

    public static final class CalcEvent implements Command {
        final long requestId;
        final int a;
        final int b;
        final int c;
        final boolean isMatch;

        public CalcEvent(long requestId, int a, int b, int c, boolean isMatch) {
            this.requestId = requestId;
            this.a = a;
            this.b = b;
            this.c = c;
            this.isMatch = isMatch;
        }

        public String toString() {
            final String format = "requestId: %d a: %d b: %d c: %d isMatch: %b";
            return String.format(format, requestId, a, b, c, isMatch);
        }
    }

    @Override
    public Receive<Reporter.Command> createReceive() {
        return newReceiveBuilder()
                   .onMessage(CalcEvent.class, this::onCalcEvent)
                   .onSignal(PostStop.class, signal -> onPostStop())
                   .build();
    }

    private Behavior<Reporter.Command> onCalcEvent(CalcEvent calcEvent) {
        getContext().getLog().info("TRACER Reporter received: {}", calcEvent.toString());

        return this;
    }

    private Behavior<Command> onPostStop() {
         getContext().getLog().info("TRACER reporter STOPPED");
         return Behaviors.stopped();
    }
}
