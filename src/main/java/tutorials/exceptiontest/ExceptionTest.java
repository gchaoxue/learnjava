package tutorials.exceptiontest;

import java.util.ArrayList;
import java.util.List;

public class ExceptionTest {
    public static void main(String[] args) {
        List<Exception> exceptionList = new ArrayList<Exception>();

        for (int i=0;i<10;i++){
            System.out.println("running: OK");
        }

        System.out.println(" [*] run an illegal code there");
        Integer nullInteger = null;
        try {
            nullInteger++;
        } catch (Exception e){
            exceptionList.add(e);
        }

        for (int i=0;i<10;i++){
            System.out.println("still running: OK");
        }
        for (Exception e : exceptionList){
            System.out.println("print runtime exception: ");
            String errorInfo = "daksdlkasdklas " + e.toString();
            System.out.println(errorInfo);
        }
    }
}
