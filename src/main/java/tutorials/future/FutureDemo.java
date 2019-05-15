package tutorials.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by gchaoxue on 2019/5/14
 */
public class FutureDemo {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<Integer> calculate(Integer input) {
        return executor.submit(() -> {
            Thread.sleep(1000);
            return input * input;
        });
    }

    public static void main(String[] args) {
        System.out.println("CALC START");
        Future<Integer> future = (new FutureDemo()).calculate(10);

        try {
            Integer res = future.get();
            System.out.println("CALC RES: " + res);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
