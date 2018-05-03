package tutorials.staticandfinal;

public class StaticFinalTest {
    private static int staticValue = 3;
    private static final int finalValue = 1;

    public static void main(String[] args) {
        staticValue++;
        System.out.println(staticValue);

        // error
        // finalValue++;
    }
}
