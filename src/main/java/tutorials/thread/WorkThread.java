package tutorials.thread;

public class WorkThread extends Thread {

    private int work_time = 0;
    WorkThread(int work_time){
        this.work_time = work_time;
    }

    // 将线程任务实现在run()函数中
    @Override
    public void run(){
        super.run();
        String thread_id = String.valueOf(this.getId());
        for (int i=0; i<this.work_time; i++){
            System.out.println(String.valueOf(i) + " in thread " + thread_id);
        }
    }
}
