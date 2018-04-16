package tutorials.jedistest;

import com.sun.xml.internal.ws.server.provider.ProviderInvokerTube;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.*;

public class JedisTest {
    private static final int SAVE_REDIS_OPT_NUM = 50000;
    private static final long NANO2MILI = 1000000;

    public static void main(String[] args) throws IOException {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "10.16.18.233", 6379);
        Jedis jedis = null;
        try {
            jedis = pool.getResource();

            // 生成一个Map
            int opt_num = SAVE_REDIS_OPT_NUM;
            Random rand = new Random();
            Map<String, String> map = new HashMap<String, String>();
            for (int i=0; i<opt_num; i++) {
                String key = String.valueOf(rand.nextInt(1000000));
                String value = String.valueOf(rand.nextInt(100000));
                if (!map.containsKey(key)) {
                    map.put(key, value);
                }
            }

            System.out.println("map size: " + String.valueOf(opt_num));

            saveToRedisNorm(map, jedis);
            saveToRedisPipeline(map, jedis);
            saveToRedisTranssaction(map, jedis);
            saveToRedisTransInPages(map, jedis);

        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
        pool.close();
    }

    public static void saveToRedisNorm(Map<String, String> map, Jedis jedis) {
        System.out.println(" [x] saving redis in normal way");
        long timeConsume = System.nanoTime();
        long nanoTime = 0;
        for (Map.Entry<String, String> e : map.entrySet()) {
            nanoTime = System.nanoTime();
            jedis.set(e.getKey(), e.getValue());
            nanoTime = System.nanoTime() - nanoTime;
        }
        timeConsume = System.nanoTime() - timeConsume;
        System.out.println(" [x] step consume: " + (nanoTime/1000) + "(μs)");
        System.out.println(" [x] time consume: " + (timeConsume/1000) + "(μs)");
    }

    public static void saveToRedisPipeline(Map<String, String> map, Jedis jedis) throws IOException {
        long timeConsume = System.nanoTime();
        System.out.println(" [x] saving redis using pipeline");
        Pipeline p = jedis.pipelined();
        for (Map.Entry<String, String> e : map.entrySet()) {
            p.set(e.getKey(), e.getValue());
        }
        p.sync();
        p.close();
        timeConsume = System.nanoTime() - timeConsume;
        System.out.println(" [x] time consume: " + (timeConsume/1000) + "(μs)");
    }

    public static void saveToRedisTranssaction(Map<String, String> map, Jedis jedis) throws IOException {
        long timeConsume = System.nanoTime();
        System.out.println(" [x] saving redis using transaction");
        Transaction tx = jedis.multi();
        for (Map.Entry<String, String> e : map.entrySet()) {
            tx.set(e.getKey(), e.getValue());
        }
        tx.exec();
        tx.close();
        timeConsume = System.nanoTime() - timeConsume;
        System.out.println(" [x] time consume: " + (timeConsume/1000) + "(μs)");
    }

    public static void saveToRedisTransInPages(Map<String, String> map, Jedis jedis) throws IOException {
        long timeConsume = System.nanoTime();
        System.out.println(" [x] saving redis using transaction");
        Transaction tx = jedis.multi();
        int trans_limit = 10000;
        int cnt = 0;
        for (Map.Entry<String, String> e : map.entrySet()) {
            cnt++;
            tx.set(e.getKey(), e.getValue());
            if (cnt > 1 && cnt %trans_limit==0) {
                tx.exec();
                tx = jedis.multi();
            }
        }
        tx.exec();
        tx.close();
        timeConsume = System.nanoTime() - timeConsume;
        System.out.println(" [x] time consume: " + (timeConsume/1000) + "(μs)");
    }

}
