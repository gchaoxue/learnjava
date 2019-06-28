package demos.memory;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by gchaoxue on 2019/6/13
 */
public class Main {

    public static void main(String[] args) {

        List<String> list = new LinkedList<>();
        StringBuilder val = new StringBuilder();
        for (int i=0; i<1000; i++) val.append("p");
        for (int i=0; i<100000; i++) {
            list.add(val.toString());

            if (i % 10000 == 0) {
                System.out.println("mem usage: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
                System.out.println("max memory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB");
            }
        }

        new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                printCpuUsage();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        new Thread(() -> {
            Random random = new Random(System.currentTimeMillis());
            while(!Thread.currentThread().isInterrupted()) {
                int load = random.nextInt(10000);
                double p = 3.14218672812781281212;
                for (int i=0; i<load; i++) {
                    p = p*p;
                    p = Math.sqrt(p);
                }
            }
        }).start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void printCpuUsage() {
        OperatingSystemMXBean mxBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : mxBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            String methodName = method.getName();
            if (methodName.startsWith("get") && methodName.contains("Cpu") && methodName.contains("Load")
                    && Modifier.isPublic(method.getModifiers())) {
                Object value;
                try {
                    value = method.invoke(mxBean);
                } catch (Exception e) {
                    value = e.getMessage();
                }
                System.out.println(methodName + ": " + value);
            }
        }
    }
}
