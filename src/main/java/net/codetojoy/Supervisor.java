package net.codetojoy;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import net.codetojoy.message.*;
import net.codetojoy.util.Timer;

public class Supervisor extends AbstractBehavior<BeginProcessing> {
    private static Range range;

    public static Behavior<BeginProcessing> create(Range range) {
        Supervisor.range = range;
        return Behaviors.setup(Supervisor::new);
    }

    private Supervisor(ActorContext<BeginProcessing> context) {
        super(context);
    }

    @Override
    public Receive<BeginProcessing> createReceive() {
        return newReceiveBuilder().onMessage(BeginProcessing.class, this::onBeginProcessing).build();
    }

    private Behavior<BeginProcessing> onBeginProcessing(BeginProcessing command) {
        try {
            Timer timer = new Timer();
            // create calculator
            ActorRef<CalcRequest> calculator = getContext().spawn(Calculator.create(), "calculator");

            // create reporter
            ActorRef<CalcResponse> reporter = getContext().spawn(Reporter.create(), "reporter");

            // create workers
            ActorRef<BlockRequest> worker = getContext().spawn(Worker.create(), "workerN");

            // assign blocks to workers
            BlockRequest blockRequest = new BlockRequest(Supervisor.range, calculator, reporter);
            worker.tell(blockRequest);

            getContext().getLog().info("TRACER Supervisor {}", timer.getElapsed("onBeginProcessing"));
        } catch (Exception ex) {
            getContext().getLog().error("TRACER Supervisor caught exception! ex: {}", ex.getMessage());
        }

        return this;
    }

    /*
    protected void sendDoneMessage(String caseId, String name, ActorRef<EmitCase> replyTo) {
        String payload = "";
        boolean isDone = true;
        ActorRef<ParseRow> parser = getGreeterByCaseId(caseId);
        parser.tell(new ParseRow(caseId, payload, isDone, name, replyTo));
    }

    protected void sendMessage(String caseId, String payload, String name, ActorRef<EmitCase> replyTo) {
        boolean isDone = false;
        ActorRef<ParseRow> parser = getGreeterByCaseId(caseId);
        parser.tell(new ParseRow(caseId, payload, isDone, name, replyTo));
    }
    */
}
