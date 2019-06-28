package demos.leaderselection;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by gchaoxue on 2019/5/30
 */
public class Main {

    public static void main(String[] args) {
//        SimpleSwitchingHaState haState = new SimpleSwitchingHaState(5000);
//        haState.start();

//        String zkConnect = "10.16.18.208:2181";
//        String serviceName = "test-ha";
//        String instanceId = "2";
//        ZookeeperHaStateImpl haState = new ZookeeperHaStateImpl(serviceName, zkConnect, instanceId);
//
//        HaWorkerAdapter adapter = new SimpleHaWorkerAdapter();
//        HaWorker haWorker = new HaWorker(instanceId, haState, adapter);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        haWorker.start();

//        Thread thread = new Thread(() -> {
//            interrupt();
//        });
//
//        thread.start();
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        thread.interrupt();

        foo();
    }

    static void foo() {
        String id = UUID.randomUUID().toString() + "-" + 197;
        System.out.println(id);

        int dashIndex = id.lastIndexOf('-');
        String prefix = id.substring(0, dashIndex);
        System.out.println(prefix);

        String suffix = id.substring(dashIndex + 1, id.length());
        System.out.println(suffix);

        Set<Pair<UUID, Integer>> set = new HashSet<>();
        set.add(new Pair<>(UUID.fromString(prefix), 197));

        if (set.contains(new Pair<>(UUID.fromString(prefix), Integer.valueOf(suffix)))) {
            System.out.println("contains");
        }

        String testString = UUID.randomUUID().toString() + "-";

        System.out.println("string: " + testString);
        System.out.println("last index of '-' : " + testString.lastIndexOf('*'));
        System.out.println("length of the string: " + testString.length());
    }

    static void interrupt() {
        while(!Thread.interrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("sleep interrupted");
                Thread.currentThread().interrupt();
            }
            System.out.println("still doing on step");
        }

        System.out.println("still finishing the job");
    }
}
