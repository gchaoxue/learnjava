package tutorials.jedistest;

import com.sun.xml.internal.ws.server.provider.ProviderInvokerTube;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.*;

public class JedisTest {
    private static final int SAVE_REDIS_OPT_NUM = 1000000;
    private static final long NANO2MILI = 1000000;

    public static void main(String[] args) throws IOException {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "10.16.18.233", 6379);
        Jedis jedis = null;
        Jedis jd2 = null;
        try {
            jedis = pool.getResource();
            jd2 = pool.getResource();
            /*
            jedis.set("foo", "bar");
            String foobar = jedis.get("foo");
            jedis.zadd("sose", 0, "car");
            jedis.zadd("sose", 0, "bike");
            Set<String> sose = jedis.zrange("sose", 0, -1);
            */

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


            long timeConsume;
            //将Map 写入redis

            jedis.flushAll();
            timeConsume = System.nanoTime();
            //saveToRedisNorm(map, jedis);
            timeConsume = System.nanoTime() - timeConsume;
            System.out.println(" [*] time consume none-pipeline: " + String.valueOf(timeConsume/NANO2MILI) + "(ms)");

            jedis.flushAll();
            timeConsume = System.nanoTime();
            //saveToRedisPipeline(map, jedis);
            timeConsume = System.nanoTime() - timeConsume;
            System.out.println(" [*] time consume pipeline: " + String.valueOf(timeConsume/NANO2MILI) + "(ms)");

            jedis.flushAll();
            timeConsume = System.nanoTime();
            saveToRedisTransInPages(map, jedis);
            timeConsume = System.nanoTime() - timeConsume;
            System.out.println(" [*] time consume transaction in pages: " + String.valueOf(timeConsume/NANO2MILI) + "(ms)");

            jedis.flushAll();
            timeConsume = System.nanoTime();
            saveToRedisTranssaction(map, jedis);
            timeConsume = System.nanoTime() - timeConsume;
            System.out.println(" [*] time consume transaction: " + String.valueOf(timeConsume/NANO2MILI) + "(ms)");



        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
        pool.close();
    }

    public static void saveToRedisNorm(Map<String, String> map, Jedis jedis) {
        System.out.println(" [x] saving redis in normal way");
        for (Map.Entry<String, String> e : map.entrySet()) {
            jedis.set(e.getKey(), e.getValue());
        }
    }

    public static void saveToRedisPipeline(Map<String, String> map, Jedis jedis) throws IOException {
        System.out.println(" [x] saving redis using pipeline");
        Pipeline p = jedis.pipelined();
        for (Map.Entry<String, String> e : map.entrySet()) {
            p.set(e.getKey(), e.getValue());
        }
        p.sync();
        p.close();
    }

    public static void saveToRedisTranssaction(Map<String, String> map, Jedis jedis) throws IOException {
        System.out.println(" [x] saving redis using transaction");
        Transaction tx = jedis.multi();
        for (Map.Entry<String, String> e : map.entrySet()) {
            tx.set(e.getKey(), e.getValue());
        }
        tx.exec();
        tx.close();
    }

    public static void saveToRedisTransInPages(Map<String, String> map, Jedis jedis) throws IOException {
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
    }

}
