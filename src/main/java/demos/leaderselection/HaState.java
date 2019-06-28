package demos.leaderselection;

/**
 * Created by gchaoxue on 2019/5/30
 */
public interface HaState {

    public enum State {
        NEW,
        SLAVE,
        MASTER,
        STOP
    }

    State getState();
}
