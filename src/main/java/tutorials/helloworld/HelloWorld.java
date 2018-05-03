package tutorials.helloworld;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class HelloWorld {
    private static final String str = "Hello, world!";
    public static void main(String[] args) {
        int i = 987654321;

        System.out.println(String.format("***%4d***\n",i));
        System.out.println(str);

        String names[] = new String[]{"tom", "bay", "den"};
        List a = Arrays.asList(names);
        System.out.println(a.size());
        for (Object e : a) {
            System.out.println(e);
        }

        try {
            throw new Exception();
        } catch (Exception e) {
            if (e.getClass().getName().equals(ClassNotFoundException.class.getName())) {
                System.out.println("EEE");
            }
            else {
                System.out.println("NNN");
            }
        }
    }
}
