package net.codetojoy;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import net.codetojoy.message.*;

public class Worker extends AbstractBehavior<BlockRequest> {
    public static Behavior<BlockRequest> create() {
        return Behaviors.setup(Worker::new);
    }

    private Worker(ActorContext<BlockRequest> context) {
        super(context);
    }

    @Override
    public Receive<BlockRequest> createReceive() {
        return newReceiveBuilder().onMessage(BlockRequest.class, this::onBlockRequest).build();
    }

    private Behavior<BlockRequest> onBlockRequest(BlockRequest blockRequest) {

        Range range = blockRequest.range;
        for (int a = range.low; a <= range.high; a++) {
            for (int b = range.low; b <= range.high; b++) {
                for (int c = range.low; c <= range.high; c++) {
                    CalcRequest calcRequest = new CalcRequest(a, b, c, blockRequest.reporter);
                    blockRequest.calculator.tell(calcRequest);
                }
            }
        }

        return this;
    }
}
