package tutorials.abstraction;

/**
 * Created by gchaoxue on 2018/12/4
 */
public abstract class DefaultWorker implements Worker {

    String value = "void value";

    @Override
    public void prepare() {
        // default implementation there
        // when the default impl of this process abstraction is changed
        // you will just need to changed code once there other than
        // applying the changes every where
        value = "standard value";
        System.out.println("set value: " + value);
    }
}
