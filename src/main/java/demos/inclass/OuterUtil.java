package demos.inclass;

/**
 * Created by gchaoxue on 2019/6/4
 */
public class OuterUtil {

    public static void changeOffset(Offsets offset) {
        offset.eventOffset++;
        offset.commandOffset++;
    }
}
