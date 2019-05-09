package tutorials.argument;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gchaoxue on 2018/12/10
 */
public class ArgumentTest {

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();

        map.put("one", "1");

        if (map.get("one").equals("1")) {
            System.out.println("YES");
        }
    }
}
