package demos.leaderselection;

/**
 * Created by gchaoxue on 2019/5/30
 */
public class Main {

    public static void main(String[] args) {

        SimpleSwitchingHaState haState = new SimpleSwitchingHaState(5000);
        haState.start();

        HaWorkerAdapter adapter = new SimpleHaWorkerAdapter();
        HaWorker haWorker = new HaWorker("1", haState, adapter);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        haWorker.start();
    }
}
