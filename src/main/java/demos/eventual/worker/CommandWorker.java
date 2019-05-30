package demos.eventual.worker;

import demos.eventual.Command;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

/**
 * Created by gchaoxue on 2019/5/30
 */
public class CommandWorker {

    Thread mainThread;

    String name;

    public CommandWorker(String name) {
        this.name = name;
        mainThread = new Thread(() -> {
            int cnt = 100;
            while (cnt-- > 0 && !Thread.currentThread().isInterrupted()) {
                System.out.println("thread-" + name + "doing work " + cnt + " ...");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("thread-" + name + " interrupted, cnt = " + cnt);
        });
    }

    public void start() {
        this.mainThread.start();
    }

    public void stop() {
        System.out.println("try to stop");
        this.mainThread.interrupt();
    }

    public static void main(String[] args) {
        CommandWorker worker = new CommandWorker("1");
        CommandWorker worker1 = new CommandWorker("2");
        worker.start();
        worker1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        worker.stop();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        worker1.stop();
    }
}
