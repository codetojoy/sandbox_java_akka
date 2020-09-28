package net.codetojoy.message;

import akka.actor.typed.ActorRef;

public final class CalcRequest {
    public final int a;
    public final int b;
    public final int c;
    public final ActorRef<CalcResponse> replyTo;

    public CalcRequest(int a, int b, int c, ActorRef<CalcResponse> replyTo) {
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
