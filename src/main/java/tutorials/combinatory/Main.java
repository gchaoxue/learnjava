package tutorials.combinatory;

import java.io.*;

/**
 * Created by gchaoxue on 2018/12/5
 */
public class Main {

    public interface Logger {
        void print(int level, String msg);
        void println(int level, String msg);
        void logException(Throwable e);
    }

    public static void main(String[] args) {
        File file;
        InputStream inputStream;
        OutputStream outputStream;

        Writer writer;

        String s;
    }
}
