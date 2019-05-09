package tutorials.learnInterface;

public class InterfaceTest {

    static class TestImpl implements MyInterface {

        @Override
        public void doWork() {

        }

        public void printValue() {
                System.out.println(MyInterface.staticValue);
        }
    }

    public static void main(String[] args) {
        MyInterface interfaceForCat = new MyInterface() {
            public void doWork() {
                System.out.println("This is cat doing work.");
            }
        };
        interfaceForCat.doWork();

        MyInterface interfaceForDog = new MyInterface() {
            public void doWork() {
                System.out.println("This is dog doing work.");
            }
        };
        interfaceForDog.doWork();


        TestImpl test = new TestImpl();
        test.printValue();
    }
}
