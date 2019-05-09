package tutorials.abstraction;

/**
 * Created by gchaoxue on 2018/12/4
 */
public class BlueWorker extends DefaultWorker {

    @Override
    public void work() {
        System.out.println("in blue: " + value);
    }

    @Override
    public void sayWorker() {
        System.out.println("override default method");
    }

    @Override
    public void workMayError() {

    }


    public static void main(String[] args) {

        BlueWorker blueWorker = new BlueWorker();
//        blueWorker.prepare();
        blueWorker.work();
        blueWorker.sayWorker();

//        class RedWorker extends DefaultWorker {
//
//            @Override
//            public void work() {
//                System.out.println("in red: " + value);
//            }
//        }
//
//        RedWorker redWorker = new RedWorker();
//        redWorker.prepare();
//        redWorker.work();
//        redWorker.sayWorker();
    }
}
