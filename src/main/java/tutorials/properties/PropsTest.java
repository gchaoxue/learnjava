package tutorials.properties;

import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;

import java.util.Properties;

public class PropsTest {

    public String test_pro = null;
    static {
        loadInfo();
    }

    public static void loadInfo() {

        String nullStr = null;
        try {
            nullStr.toString();
        } catch (Exception e) {
            System.out.println("NULL EXCEPTION");
            return;
        } finally {
            System.out.println("FINAL");
        }

        System.out.println("IN HERE");

    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("name", "gchaoxue");
        properties.put("id", "423");

        System.out.println(properties.getProperty("name"));

        properties.clear();
        //properties.put("name", "Tom");
        System.out.println(properties.getProperty("name"));
    }
}
