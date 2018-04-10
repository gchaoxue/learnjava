package tutorials.loggertest;
import java.io.*;

public class LoggerTest {
    public static void main(String[] args) throws IOException{
        String fileName = "C:\\Users\\User\\IdeaProjects\\learnjava\\logs\\info.log";
        PrintStream out = new PrintStream(new FileOutputStream(fileName));
        System.setOut(out);

        System.out.println("dsahdaskdsa");
    }
}
