package tutorials.threadPoolExecutor;

import tutorials.thread.WorkRunnable;

import java.util.Random;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//检验 ThreadPoolExecutor 线程并发模式
public class ThreadPoolExecutorTest {
    private static final int THREAD_POOL_SZIE = 8;

    public static void main(String[] args) {
        // 实例化一个Executor对象，用于管理线程池和任务调度
        ExecutorService executor  = Executors.newFixedThreadPool(THREAD_POOL_SZIE);
        for (int i=0; i<100; i++) {
            Random rand = new Random();
            int work_load = rand.nextInt(1000) + 1;
            WorkRunnable wr = new WorkRunnable(String.valueOf(i), work_load);
            // 将任务线程放入线程池
            executor.execute(wr);
        }

    }
}
