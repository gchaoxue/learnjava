package tutorials.helloworld;

public class HelloWorld {
    private static final String str = "Hello, world!";
    public static void main(String[] args) {
        int i = 987654321;

        System.out.println(String.format("***%4d***\n",i));
        System.out.println(str);
    }
}
