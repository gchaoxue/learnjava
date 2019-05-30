package demos.leaderselection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gchaoxue on 2019/5/30
 */
public class HaWorker {

    private static final Logger LOG = LoggerFactory.getLogger(HaWorker.class);

    private String instanceId;
    private Thread mainThread;

    // todo: should it use volatile here?
    private HaState haState;

    // todo: supporting standalone mode switch

    private HaWorkerAdapter workerAdaptor;

    // HaWorker having its local-state variable for stating the current working state.
    private HaState.State localState;

    public HaWorker(String instanceId, HaState haState, HaWorkerAdapter workerAdaptor) {
        // haState will not be instanced or be initialized within the HaWorker,
        // the haState may having it's leader state before the HaWorker is started.
        this.instanceId = instanceId;
        this.haState = haState;
        this.workerAdaptor = workerAdaptor;
        this.localState = HaState.State.NEW;
    }

    public void start() {
        // todo: check if the haState is usable
        mainThread = new Thread(() -> {
            // run before
            boolean okToStart = true;
            try {
                workerAdaptor.before();
            } catch (HaWorkerException e) {
                // run before failed, should not start the main process
                LOG.error("fail to run adaptor.before()", e);
//                Thread.currentThread().interrupt();
                okToStart = false;
            }

            // into the main process
            if (okToStart) {
                // todo: how to check if the localState is legal?
                localState = HaState.State.SLAVE;
                // the while loop may be interrupted by outer method invoke
                while(!Thread.interrupted()) {
                    // todo: do we need this here? the loop will be break once the newState<-STOP is encountered
//                    if (localState == HaState.State.STOP) break;
                    // read new state from state service
                    HaState.State newState = haState.getState();
                    if (newState == HaState.State.STOP) {
                        // todo: list the situations when newState is STOP
                        //   1. some network changing casing the zookeeper release the leadership
                        //      of the current instance
                        localState = newState;
                        break;
                    }
                    doWork(localState, newState);
                    localState = newState;
                }
                LOG.info("worker stopped, current HA state: {}", localState);
                workerAdaptor.after();
            }
        }, "instance-" + instanceId + "-mainThread");

        mainThread.start();
    }

    // workflow control according to statement switching
    private void doWork(HaState.State localState, HaState.State newState) {
        switch (localState) {
            case SLAVE:
                if (newState == HaState.State.SLAVE) {
                    // still SLAVE, continue slave work
                    workerAdaptor.doOneStepSlave();
                }
                else if (newState == HaState.State.MASTER) {
                    // from SLAVE to MASTER,
                    try {
                        workerAdaptor.becomeMaster();
                    } catch (HaWorkerException e) {
                        LOG.error("fail to run adaptor.becomeMaster()", e);
                        // stop the worker using the local state control
                        // todo: what to do next?
                        //   1. exit the main thread
                        //   2. try to stop other threads(processing/sending)?
                        // currently, before the worker is doing MASTER work,
                        // there is only one threads:
                        //     1. worker main thread which is doing work of event pulling and applying
                        // so, we do not need to do extra work to stop some other thread
                        // todo: test what it will actually behave when this situation happens
                        this.localState = HaState.State.STOP;
                    }
                }
                else {
                    // this is impossible, :)
                    // todo: how to throw Exceptions from runnable run method?
//                    throw new HaWorkerException("illegal statement switching: " +
//                            "from<" + localState + "> to<" + newState + ">");
//                    Thread.currentThread().interrupt();
                    LOG.error("illegal statement switching: from<{}> to<{}>", localState, newState);
                }
                break;
            case MASTER:
                if (newState == HaState.State.MASTER) {
                    // still MASTER, continue master work
                    workerAdaptor.doOneStepMaster();
                }
                else if (newState == HaState.State.SLAVE) {
                    // switching not defined, MASTER should either PAUSE the work or STOP it
                    LOG.error("illegal statement switching: from<{}> to<{}>", localState, newState);
                }
                else {
                    // this is impossible, :)
                    LOG.error("illegal statement switching: from<{}> to<{}>", localState, newState);
                }
                break;
            default:
                break;
        }
    }
}
