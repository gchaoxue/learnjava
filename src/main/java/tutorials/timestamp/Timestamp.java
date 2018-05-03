package tutorials.timestamp;

import java.io.IOException;
import java.util.Date;

public class Timestamp {
    public static void main(String[] args) throws IOException{
        long timeStamp = System.nanoTime();

        Date date = new Date(System.currentTimeMillis());


        System.out.println(date.getTime());
    }
}
