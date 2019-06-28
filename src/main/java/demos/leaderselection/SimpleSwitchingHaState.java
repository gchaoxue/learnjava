package demos.leaderselection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gchaoxue on 2019/5/30
 */
public class SimpleSwitchingHaState implements HaState {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleSwitchingHaState.class);

    private volatile State state;
    private long switchInterval;

    public SimpleSwitchingHaState(long switchInterval) {
        this.switchInterval = switchInterval;
        this.state = State.NEW;
    }

    public void start() {

        new Thread(()->{
            LOG.info("start and waiting");

            LOG.info("set state NEW");
            state = State.NEW;

            waitFor();
            LOG.info("set state SLAVE");
            state = State.SLAVE;

            waitFor();
            LOG.info("set state MASTER");
            state = State.MASTER;

            waitFor();
            LOG.info("set state STOP");
            state = State.STOP;

            waitFor();
            LOG.info("set state MASTER");
            state = State.MASTER;
        }).start();
    }

    private void waitFor() {
        try {
            Thread.sleep(switchInterval);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public State getState() {
        return this.state;
    }
}
