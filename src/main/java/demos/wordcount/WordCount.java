package demos.wordcount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Count the words' appearance.
 *
 * Created by gchaoxue on 2019/5/9
 */
public class WordCount {

    private static final Logger LOG = LoggerFactory.getLogger(WordCount.class);

//    private List<Pair<String/*word*/, Integer/*count*/>> wordCount = new ArrayList<>();

    // brute-forced
    public static Map<String/*word*/, Long/*count*/> count(List<List<String>> data) {
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
//        for (String word : wordCount.keySet()) {
//            LOG.info("count result: word<{}>, count<{}>", word, wordCount.get(word));
//        }
        return wordCount;
    }

    public static void top(Map<String, Long> wordCount, int rank) {
        List<Map.Entry<String, Long>> wordCountList = new ArrayList<>(wordCount.entrySet());
        wordCountList.sort((o1, o2) -> {
            if (o1.getValue().equals(o2.getValue()))
                return 0;
            else
                return o1.getValue() > o2.getValue() ? -1 : 1;
        });

        int rankCnt = 0;
        for (Map.Entry<String, Long> e : wordCountList) {
            LOG.info("count result: word<{}>, code<{}>, count<{}>", e.getKey(), e.getKey().getBytes(Charset.defaultCharset()),e.getValue());
            rankCnt++;
            if (rankCnt == rank)
                break;
        }
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

        long begin = System.nanoTime();
        Map<String, Long> wordCount = WordCount.count(data);
        long end = System.nanoTime();

        WordCount.top(wordCount, 10);

        LOG.info("word count finished in <{}>ms", (end - begin) / 1000000);
    }
}
