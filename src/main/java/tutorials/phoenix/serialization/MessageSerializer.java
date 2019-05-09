package tutorials.phoenix.serialization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by gchaoxue on 2018/12/18
 */
public class MessageSerializer {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSerializer.class);

    public static byte[] serialize(Serializable message) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);

            oo.writeObject(message);
            bytes = bo.toByteArray();

            oo.close();
            bo.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return bytes;
    }

    public static Object deserialize(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();

            oi.close();
            bi.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return obj;
    }
}
