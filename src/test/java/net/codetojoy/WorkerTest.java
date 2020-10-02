
package net.codetojoy;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import org.junit.ClassRule;
import org.junit.Test;
import org.scalatestplus.junit.JUnitSuite;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

import static net.codetojoy.Supervisor.ProcessRangeAckEvent;
import static net.codetojoy.Worker.ProcessRangeCommand;

public class WorkerTest extends JUnitSuite {

    @ClassRule public static final TestKitJunitResource testKit = new TestKitJunitResource();

    @Test
    public void testReplyToProcessRangeCommand() {
        // couldn't get this to work, due to generics
        // see this :  https://doc.akka.io/docs/akka/current/typed/guide/tutorial.html
        // and this :  https://github.com/akka/akka/tree/v2.6.9/akka-docs/src/test/java/jdocs/typed 

        /*
        TestProbe<ProcessRangeAckEvent> probe = testKit.createTestProbe(ProcessRangeAckEvent.class);
        ActorRef<ProcessRangeAckEvent> probeRef = probe.getRef();
        ActorRef<Worker.Command> worker = testKit.spawn(Worker.create());

        long requestId = 6160;
        Range range = new Range(1,10);
        ActorRef<Calculator.Command> calculator = testKit.spawn(Calculator.create());
        ActorRef<Reporter.Command> reporter = testKit.spawn(Reporter.create());

        worker.tell(new ProcessRangeCommand(requestId, range, calculator, reporter, probeRef));
        Supervisor.Command processRangeAckEvent = probe.receiveMessage();

        groupActor.tell(new RequestTrackDevice("group", "device", probe.getRef()));
        DeviceRegistered registered1 = probe.receiveMessage();

        // another deviceId
        groupActor.tell(new RequestTrackDevice("group", "device3", probe.getRef()));
        DeviceRegistered registered2 = probe.receiveMessage();
        assertNotEquals(registered1.device, registered2.device);

        // Check that the device actors are working
        TestProbe<Device.TemperatureRecorded> recordProbe =
            testKit.createTestProbe(Device.TemperatureRecorded.class);
        registered1.device.tell(new Device.RecordTemperature(0L, 1.0, recordProbe.getRef()));
        assertEquals(0L, recordProbe.receiveMessage().requestId);
        registered2.device.tell(new Device.RecordTemperature(1L, 2.0, recordProbe.getRef()));
        assertEquals(1L, recordProbe.receiveMessage().requestId);
        */
    }
}
