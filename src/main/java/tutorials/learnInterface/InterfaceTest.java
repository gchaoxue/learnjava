package tutorials.learnInterface;

public class InterfaceTest {
    public static void main(String[] args) {
        MyInterface interface_for_cat = new MyInterface() {
            public void doWork() {
                System.out.println("This is cat doing work.");
            }
        };
        interface_for_cat.doWork();

        MyInterface interface_for_dog = new MyInterface() {
            public void doWork() {
                System.out.println("This is dog doing work.");
            }
        };
        interface_for_dog.doWork();
    }
}
