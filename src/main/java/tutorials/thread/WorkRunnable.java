package tutorials.thread;

//
public class WorkRunnable implements Runnable {
    private int run_time = 0;
    private String name = "";
    public WorkRunnable(String name, int run_time){
        this.name = name;
        this.run_time = run_time;
    }
    public String getName(){
        return this.name;
    }
    public void run(){
        try{
            Thread.sleep(run_time);
        }catch (InterruptedException _ignore){
            Thread.currentThread().interrupt();
        }
        System.out.println(" [x] Running work " + this.name + " runtime " + String.valueOf(run_time) + " in thread " + Thread.currentThread().getName());
    }
}
