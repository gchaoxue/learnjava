package demos.leaderselection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gchaoxue on 2019/5/30
 */
public class SimpleHaWorkerAdapter implements HaWorkerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleHaWorkerAdapter.class);

    @Override
    public void before() throws HaWorkerException {
        LOG.info("do before");
    }

    @Override
    public void becomeMaster() throws HaWorkerException {
        LOG.info("do becomeMaster");
        waitFor(500);
    }

    @Override
    public void doOneStepSlave() {
        LOG.info("do oneStepSlave");
        waitFor(1000);
    }

    @Override
    public void doOneStepMaster() {
        LOG.info("do oneStepMaster");
        waitFor(1000);
    }

    @Override
    public void after() {
        LOG.info("do after");
        waitFor(2000);
    }

    private void waitFor(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
