package tutorials.callback;

import tutorials.learnInterface.MyInterface;

public class CallbackTest {
    public static void main(String[] args) {
        Callbacker callbacker = new Callbacker();
        callbacker.may_call(new MyInterface() {
            public void doWork() {
                System.out.println("This may be call by a Callbacker.");
            }
        });
    }
}
