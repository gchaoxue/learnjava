package demos.wordcount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Count the words' appearance.
 *
 * Created by gchaoxue on 2019/5/9
 */
public class WordCount {

    private static final Logger LOG = LoggerFactory.getLogger(WordCount.class);

//    private List<Pair<String/*word*/, Integer/*count*/>> wordCount = new ArrayList<>();

    // brute-forced
    public static void count(List<List<String>> data) {
        Map<String, Long> wordCount = new HashMap<>();
        for (List<String> sl : data) {
            for (String s : sl) {
                if (wordCount.containsKey(s)) {
                    wordCount.put(s, wordCount.get(s) + 1);
                }
                else {
                    wordCount.put(s, 1L);
                }
            }
        }

        LOG.info("word counting finished: total-words<{}>", wordCount.size());
        // todo: which is better? iterate Map.keySet or Map.entrySet
        for (String word : wordCount.keySet()) {
            LOG.info("count result: word<{}>, count<{}>", word, wordCount.get(word));
        }
    }

    public static void top(Map<String, Long> wordCount, int rank) {
        wordCount.entrySet().stream().sorted()
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\User\\IdeaProjects\\learnjava\\src\\main\\java\\demos\\wordcount\\data\\words.dat";
        DataLoader dataLoader = new DataLoader(path);
        try {
            dataLoader.load();
        } catch (IOException e) {
            LOG.error("load data file error", e);
        }

        List<List<String>> data = dataLoader.getData();

        WordCount.count(data);
    }
}
