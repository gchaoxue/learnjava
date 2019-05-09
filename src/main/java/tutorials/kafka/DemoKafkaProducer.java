package tutorials.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tutorials.phoenix.message.Message;
import tutorials.phoenix.serialization.MessageSerializer;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by gchaoxue on 2018/12/17
 */
public class DemoKafkaProducer {

    private static final Logger LOG = LoggerFactory.getLogger(DemoKafkaProducer.class);

    private String bootstrapServer;
    private String group; // todo: group usage in producer
    private String topic;
    private int partition = 0;  // use partition-0 as default

    /**
     * Auto reconnect capable.
     */
    private KafkaProducer<String, byte[]> producer;

    public DemoKafkaProducer(String bootstrapServer, String group, String topic) {
        this.bootstrapServer = bootstrapServer;
        this.group = group;
        this.topic = topic;
    }

    public void init() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "1");
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "1");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "32");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        properties.setProperty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "5000");
        properties.setProperty(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "6000");

        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        producer = new KafkaProducer<>(properties);
    }

    public void asyncSend(Message message) {

        // for subscription usage
        String key = message.getPayloadClassName();
        byte[] value = MessageSerializer.serialize(message);
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, partition, key, value);

        Future<RecordMetadata> result = producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (null != e) {
                    LOG.error("async-send failed: exceptionMsg<{}>", e.getMessage());
                }
            }
        });
        LOG.debug("async-send executed: payloadClass<{}>, msgId<{}>, topic<{}>",
                message.getPayloadClassName(), message.getMsgId(), topic);
    }

    public boolean syncSend(Message message) {
        String key = message.getPayloadClassName();
        byte[] value = MessageSerializer.serialize(message);
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, partition, key, value);

        RecordMetadata result;
        try {
            result = producer.send(record).get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOG.error("sync send failed: payloadClass<{}>, msgId<{}>",
                    message.getPayloadClassName(), message.getMsgId(), e);
            // todo: sync send retry?
            return false;
        }

        LOG.debug("sync-send succeed: payloadClass<{}>, msgId<{}>, topic<{}>, offset<{}>",
                message.getPayloadClassName(), message.getMsgId(), topic, result.offset());

        System.out.println("sync-send succeed: topic<" + topic + ">, offset<" + result.offset() + ">");
        return true;
    }

    public static class NeedMessage implements Serializable{
    }

    public static class NotNeedMessage implements Serializable{
    }

    public static void main(String[] args) {
        DemoKafkaProducer producer =
                new DemoKafkaProducer("10.16.18.233:9092", "test_producer", "_test");
        producer.init();



        while(true) {
            producer.syncSend(new Message(new NeedMessage()));
            producer.syncSend(new Message(new NotNeedMessage()));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
