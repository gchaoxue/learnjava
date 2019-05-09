package demos.wordcount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data Loader.
 * Input format:
 *   1. multiple lines
 *   2. arbitrary number of words each line
 *   3. separate by one or many blanks
 *
 * Output format:
 *   1. list of sentences.
 *   2. sentences in list of words
 *   3. words in String
 *
 * Created by gchaoxue on 2019/5/9
 */
public class DataLoader {

    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
    private static final String DEFAULT_PATH = "data/words.dat";

    private String filePath = "";
    private List<List<String>> data = new ArrayList<>();

    public DataLoader() {}
    public DataLoader(String filePath) {
        this.filePath = filePath;
    }

    public List<List<String>> getData() {
        return this.data;
    }

    public void load() throws IOException {
        if (filePath.isEmpty()) {
            // todo: what is the best way to indicate argument var in log message?
            LOG.warn("filePath is not defined, load data from default path<{}>", DEFAULT_PATH);
            filePath = DEFAULT_PATH;
        }

        LOG.info("load data file from path<{}>", filePath);

        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            long totalWords = 0;
            while((line = br.readLine()) != null) {
                data.add(getWords(line));
                totalWords += data.get(data.size() - 1).size();
            }

            LOG.info("read file finished: total-lines<{}>, total-words<{}>", data.size(), totalWords);
        }
    }

    private List<String> getWords(String line) {
        // get rid of punctuation
        // https://stackoverflow.com/questions/18830813/how-can-i-remove-punctuation-from-input-text-in-java

        // in case of converting string "apple(banana)" to word "applebanana"
        // you should first replace none-alphabetic char to space
        String[] words = line.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
        return Arrays.asList(words);
    }

    public static void main(String[] args) {


    }
}
