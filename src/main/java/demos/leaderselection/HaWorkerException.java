package demos.leaderselection;

/**
 * Created by gchaoxue on 2019/5/30
 */
public class HaWorkerException extends Exception {

    public HaWorkerException(String message) {
        super(message);
    }

    public HaWorkerException(String message, Throwable cause) {
        super(message, cause);
    }
}
