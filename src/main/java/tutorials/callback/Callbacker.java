package tutorials.callback;

import tutorials.learnInterface.MyInterface;

public class Callbacker {
    public void may_call(MyInterface myInterface){
        myInterface.doWork();
    }
}
