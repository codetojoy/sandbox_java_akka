package net.codetojoy;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import net.codetojoy.message.*;

public class Calculator extends AbstractBehavior<CalcRequest> {

    public static Behavior<CalcRequest> create() {
        return Behaviors.setup(Calculator::new);
    }

    private Calculator(ActorContext<CalcRequest> context) {
        super(context);
    }

    @Override
    public Receive<CalcRequest> createReceive() {
        return newReceiveBuilder().onMessage(CalcRequest.class, this::onCalcRequest).build();
    }

    private Behavior<CalcRequest> onCalcRequest(CalcRequest calcRequest) {
        int a = calcRequest.a;
        int b = calcRequest.b;
        int c = calcRequest.c;

        if (c == (a + b)) {
            getContext().getLog().info("TRACER Calculator match: {}", calcRequest.toString());
            boolean result = true;
            CalcResponse calcResponse = new CalcResponse(a, b, c, result);
            calcRequest.replyTo.tell(calcResponse);
        }

        return this;
    }
}
