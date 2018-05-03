package tutorials.sync;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;

import java.util.Random;

public class SyncTest {
    public static void main(String[] args) {
    }

    public void foo() {
        Random random = new Random();
        for (int i=0;i<10;i++) {
            System.out.println(i);
            try {
                Thread.sleep(random.nextInt(500));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
