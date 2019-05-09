package tutorials.abstraction;

/**
 * Created by gchaoxue on 2018/12/4
 */
public interface Worker {
    void prepare();
    void work();

    void workMayError() throws Exception;

    default void sayWorker() {
        System.out.println("this is default method of interface");
    }
}
