package demos.leaderselection;

/**
 * Created by gchaoxue on 2019/5/30
 */
public interface HaWorkerAdapter {

    void before() throws HaWorkerException;
    void becomeMaster() throws HaWorkerException;
    void doOneStepSlave();
    void doOneStepMaster();
    void after();
}
