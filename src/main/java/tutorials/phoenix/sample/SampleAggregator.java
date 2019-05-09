package tutorials.phoenix.sample;

import tutorials.phoenix.annotation.AggregatorDefinition;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gchaoxue on 2018/12/13
 */
public class SampleAggregator {

    private int cmdHandlingCounter = 0;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
    private static final Date DATE = new Date();

    public int getCmdHandlingCounter() {
        return cmdHandlingCounter;
    }

    @AggregatorDefinition(aggregatorId = "businessId")
    public SampleEvent act(SampleCommand cmd) {
        SampleEvent event = new SampleEvent();
        event.setBusinessId(cmd.getBusinessId());
        event.setMessage(cmd.getMessage() + " handled at " + DATE_FORMAT.format(DATE));
        event.setBusinessValue(cmd.getBusinessValue());
        event.setBusinessStatus(cmdHandlingCounter % 5);

        return event;
    }
}
