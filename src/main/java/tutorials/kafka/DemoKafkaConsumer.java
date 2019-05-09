package tutorials.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tutorials.phoenix.message.Message;
import tutorials.phoenix.serialization.MessageSerializer;

import java.time.Duration;
import java.util.*;

/**
 * Created by gchaoxue on 2018/12/18
 */
public class DemoKafkaConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(DemoKafkaProducer.class);

    private String bootstrapServer;
    private String group;
    private String topic;

    private TopicPartition topicPartition;
    private int partition = 0;  // set to [0] as default
    private long nextOffset = 0;  // set to [0] as default

    /**
     * Kafka is not supporting multi-subscription.
     */
    private Set<String> subscription = new HashSet<>();

    /**
     *  Auto offset commit enabled.
     */
    private KafkaConsumer<String/*payloadClassName*/, byte[]/*messageData*/> consumer;

    public DemoKafkaConsumer(String bootstrapServer, String group, String topic, int partition) {
        this.bootstrapServer = bootstrapServer;
        this.group = group;
        this.topic = topic;
        this.partition = partition;
    }

    public void init() {
        // 1. config arguments value validation
        if (bootstrapServer == null || bootstrapServer.isEmpty()) {
            throw new IllegalStateException("bootstrap.server is null or empty");
        }
        if (group == null || group.isEmpty()) {
            throw new IllegalStateException("group.id is null or empty");
        }
        if (topic == null || topic.isEmpty()) {
            throw new IllegalStateException("topic is null or empty");
        }

        // 2. construct kafka consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "10");
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "32");
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");  // turn off auto.commit
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        // new kafka consumer
        topicPartition = new TopicPartition(topic, partition);
        consumer = new KafkaConsumer<>(properties);
        consumer.assign(Arrays.asList(topicPartition));
        nextOffset = consumer.position(topicPartition);

        // todo: log config details
        LOG.info("consumer nextOffset: " + nextOffset);
    }

    public void setNextOffset(long offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("invalid offset: " + offset);
        }

        consumer.seek(topicPartition, offset);
        nextOffset = consumer.position(topicPartition);
        LOG.info("set consumer nextOffset to: " + nextOffset);
    }

    public long getNextOffset() {
        return consumer.position(topicPartition);
    }

    public long getMinOffset() throws Exception{
        Map<TopicPartition, Long> offsets = consumer.beginningOffsets(Arrays.asList(topicPartition));
        Long offset = offsets.get(topicPartition);
        if (null != offset) {
            return offset;
        } else {
            throw new Exception("get min offset failed");
        }
    }

    public long getMaxOffset() throws Exception {
        Map<TopicPartition, Long> offsets = consumer.endOffsets(Arrays.asList(topicPartition));
        Long offset = offsets.get(topicPartition);
        if (null != offset) {
            return offset;
        } else {
            throw new Exception("get max offset failed");
        }
    }

    public List<Message> pull(int time) {
        List<Message> result = new ArrayList<>();
        // 1. pull a batch of messages
        ConsumerRecords<String, byte[]> pollRecords = consumer.poll(Duration.ofMillis(time));  // todo: pull timeout?

        // 2. deserialize and filter message object
        for (ConsumerRecord<String, byte[]> record : pollRecords) {
            if (subscription == null || subscription.isEmpty() || subscription.contains(record.key())) {
                Object object = deserialize(record.value());  // todo: exception handling?
                if (null != object) {
                    Message message = (Message) object;
                    message.setOffset(record.offset());
                    result.add(message);
                }
            }
            nextOffset = record.offset() + 1;
        }

        // 3. update broker offset / commit offset
        consumer.commitSync();
        LOG.debug("sync-commit offset succeed: nextOffset<{}>", nextOffset);

        return result;
    }

    private Object deserialize(byte[] bytes) {
        return MessageSerializer.deserialize(bytes);
    }

    public void subscribe(Collection<String> subscription) {
        this.subscription.addAll(subscription);
    }

    public static void main(String[] args) {
        DemoKafkaConsumer consumer =
                new DemoKafkaConsumer("10.16.18.233:9092", "test_consumer", "_test", 0);

        consumer.init();
        consumer.subscribe(Arrays.asList(DemoKafkaProducer.NeedMessage.class.getName()));
//        try {
//            consumer.setNextOffset(consumer.getMinOffset());
//        } catch (Exception e) {
//            LOG.error("get min offset exception", e);
//        }

        while(true) {
            List<Message> messages = consumer.pull(20);
            for (Message msg : messages) {
                LOG.info("get message: msgId<{}>, className<{}>",
                        msg.getMsgId(), msg.getPayloadClassName());
            }
        }
    }
}
