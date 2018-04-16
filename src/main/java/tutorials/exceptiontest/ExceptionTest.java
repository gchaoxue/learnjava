package tutorials.exceptiontest;

import jdk.nashorn.internal.runtime.ECMAException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ExceptionTest {
    public static void main(String[] args) {
        /*
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


        System.out.println("===============================================");
        try {
            throwException();
        } catch (Exception e) {
            exceptionList.add(e);
        }

        for (Exception e : exceptionList){
            System.out.println("print runtime exception: ");
            String errorInfo = "daksdlkasdklas " + e.toString();
            System.out.println(errorInfo);
        }
        */

        tryCatchGo();

    }

    public static void throwException() throws Exception{
        Integer nullInteger = null;
        try {
            nullInteger++;
        } catch (Exception e){
            throw e;
        } finally {
            System.out.println("Throw an Exception");
        }
    }

    public static void tryCatchGo() {
        List<Exception> eList = new ArrayList<Exception>();
        for (int i=0;i<100;i++){
            System.out.println("Doing " + i);
            if (i==4) {
                try {
                    Integer nullInt = null;
                    nullInt++;
                } catch (Exception e) {
                    eList.add(e);
                }
            }
        }
        for (Exception e : eList) {
            System.out.println("catch e: " + e.toString());
        }
    }
}
