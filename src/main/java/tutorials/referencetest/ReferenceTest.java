package tutorials.referencetest;

import java.util.ArrayList;
import java.util.List;

public class ReferenceTest {
    public static void main(String[] args) {
        List<String> testList = new ArrayList<String>();
        for (int i=0;i<100;i++) {
            testList.add(String.valueOf(i));
        }
        getPram(testList);
        System.out.println(testList.size());
    }
    public static void getPram(List<String> testList) {
        testList.size();

        testList.clear();
    }
}
