package demos.inclass;

/**
 * Created by gchaoxue on 2019/6/4
 */
public class InClassControl {

    private Offsets offset = new Offsets();

    public long getEventOffset() {
        return this.offset.eventOffset;
    }

    public void changeOffset() {
        OuterUtil.changeOffset(this.offset);
    }

    public static void main(String[] args) {
        InClassControl control = new InClassControl();

        System.out.println("event offset: " + control.getEventOffset());
        control.changeOffset();
        System.out.println("event offset: " + control.getEventOffset());
    }
}
