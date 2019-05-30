package demos.memsize;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Created by gchaoxue on 2019/5/30
 */
public class Memsize {

    static class MessageId {
        UUID transId;
        int suffix;
        public MessageId(UUID transId, int suffix) {
            this.transId = transId;
            this.suffix = suffix;
        }
    }

    public static void main(String[] args) {
        Set<MessageId> set = new HashSet<>();

        int counter = 0;
        String id;
        int suffix;
        Random random = new Random(System.currentTimeMillis());
        while(counter <= 10000000) {
            id = UUID.randomUUID().toString();
            suffix = random.nextInt(100);
            set.add(new MessageId(UUID.fromString(id), suffix));
            counter++;
            if (counter % 5000 == 0) {
                System.out.println("size: " + counter);
            }
        }
        while(true) {
            System.out.println("done...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
