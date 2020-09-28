package net.codetojoy.message;

import net.codetojoy.Range;

import akka.actor.typed.ActorRef;

public final class BlockRequest {
    public final Range range;
    public final ActorRef<CalcRequest> calculator;
    public final ActorRef<CalcResponse> reporter;

    public BlockRequest(Range range, ActorRef<CalcRequest> calculator, ActorRef<CalcResponse> reporter) {
        this.range = range;
        this.calculator = calculator;
        this.reporter = reporter;
    }
}
