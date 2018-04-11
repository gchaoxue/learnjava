package tutorials.templatetest;

import java.util.ArrayList;
import java.util.List;

public class TemplateTest {
    public static void main(String[] args) {

        List<Integer> l = new ArrayList<Integer>();
        for (int i=0;i<10;i++){
            l.add(Integer.valueOf(i));
        }
        for (Integer e : l.subList(0, 3)) {
            System.out.println(e);
        }

        int big = 66;
        int sml = 55;
        int range = big < sml ? big : sml;
        List<String> stringList = new ArrayList<String>();



        //System.out.println(Math.min(big, sml));

    }
}
