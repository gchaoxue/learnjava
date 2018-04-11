package tutorials.listtemplate;

import javafx.beans.binding.ObjectExpression;

import javax.management.openmbean.ArrayType;
import java.util.ArrayList;
import java.util.List;

public class ListTemplateTest {
    public static void main(String[] args) {
        List<List<String>> transBuffer = new ArrayList<List<String>>();
        List<String> transBatch = null;
        for (int i=0; i<312; i++) {
            if (transBatch == null) {
                transBatch = new ArrayList<String>();
            }
            if (transBatch.size() == 10) {
                transBuffer.add(transBatch);
                transBatch = new ArrayList<String>();
            }
            else {
                transBatch.add(String.valueOf(i));
            }
        }

        for (List<String> ele : transBuffer) {
            for (String e : ele) {
                System.out.println(e);
            }
            System.out.println("------------------------");
        }

        List l = new ArrayList();
        l.add("adsad");
        l.add(213123);
        for (Object e : l){
            System.out.println(String.valueOf(e));
        }
    }
}
