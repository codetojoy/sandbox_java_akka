package net.codetojoy;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import net.codetojoy.message.*;

public class Reporter extends AbstractBehavior<CalcResponse> {
    public static Behavior<CalcResponse> create() {
        return Behaviors.setup(Reporter::new);
    }

    private Reporter(ActorContext<CalcResponse> context) {
        super(context);
    }

    @Override
    public Receive<CalcResponse> createReceive() {
        return newReceiveBuilder().onMessage(CalcResponse.class, this::onCalcResponse).build();
    }

    private Behavior<CalcResponse> onCalcResponse(CalcResponse calcResponse) {
        getContext().getLog().info("TRACER Reporter received: {}", calcResponse.toString());

        return this;
    }
}
