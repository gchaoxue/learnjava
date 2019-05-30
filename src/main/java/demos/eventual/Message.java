package demos.eventual;

import java.util.UUID;

/**
 * Created by gchaoxue on 2019/5/30
 */
public class Message {

    Object payload;
    Class<?> payloadClass;
    String transId;
    int msgIdSuffix = -1;
    String msgId;

    public Message(Object payload) {
        this.payload = payload;
        this.payloadClass = payload.getClass();
        this.transId = UUID.randomUUID().toString();
        this.msgIdSuffix = 0;
    }

    public Message(Object payload, Message parent) {
        if (payload == null) {
            throw new NullPointerException("payload cannot be null");
        }
        this.payload = payload;
        this.payloadClass = payload.getClass();
        if (parent != null) {
            this.transId = parent.transId;
            this.msgIdSuffix = genMsgIdSuffix(parent);
            this.msgId = genMsgId();
        }
    }

    private int genMsgIdSuffix(Message parent) {
        return parent.msgIdSuffix + 1;
    }

    private String genMsgId() {
        if (this.transId == null) {
            throw new NullPointerException("transId is null, make sure the transId is assigned" +
                    " or generated before generating the msgId");
        }
        if (this.msgIdSuffix == -1) {
            throw new IllegalStateException("msgIdSuffix is invalid, make sure the value is assigned correctly");
        }
        return this.transId + '-' + msgIdSuffix;
    }

}

