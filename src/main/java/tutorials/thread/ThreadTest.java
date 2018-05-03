package tutorials.thread;

public class ThreadTest {
    public static void main(String[] args) {

        WorkThread work_thread1 = new WorkThread(100);
        WorkThread work_thread2 = new WorkThread(100);

        work_thread1.setName("thread 1");
        // 可以用start()和run()启动线程
        work_thread1.run();
        work_thread2.start();

        WorkRunnable wr = new WorkRunnable("work_1",50);
        // 使用Runnable对象实例化一个Thread对象
        Thread runnable_executor = new Thread(wr);
        runnable_executor.run();
        runnable_executor.interrupt();
    }
}
