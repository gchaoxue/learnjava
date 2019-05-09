package tutorials.phoenix.message;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by gchaoxue on 2018/12/12
 */
public class Message implements Serializable {
    private Serializable payloadObject;
    private String payloadClassName;
    private String msgId;
    private String transId;
    private String parentMsgId;
    private String rootCmdClassName;
    private Long offset;

    public Message(Serializable payloadObject) {
        if (null != payloadObject) {
            this.payloadObject = payloadObject;
            this.payloadClassName = payloadObject.getClass().getName();
        }
        else {
            this.payloadObject = null;
            this.payloadClassName = null;
        }

        this.msgId = UUID.randomUUID().toString();
        this.transId = UUID.randomUUID().toString();
        this.parentMsgId = msgId;
        this.rootCmdClassName = payloadClassName;
    }

    public Message(Serializable payloadObject, Message parentMsg) {
        if (null != payloadObject) {
            this.payloadObject = payloadObject;
            this.payloadClassName = payloadObject.getClass().getName();
        }
        else {
            this.payloadObject = null;
            this.payloadClassName = null;
        }

        this.msgId = UUID.randomUUID().toString();
        this.transId = parentMsg.transId;
        this.parentMsgId = parentMsg.msgId;
        this.rootCmdClassName = parentMsg.rootCmdClassName;
    }

    public Object getPayloadObject() {
        return payloadObject;
    }

    public String getPayloadClassName() {
        return payloadClassName;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getTransId() {
        return transId;
    }

    public String getParentMsgId() {
        return parentMsgId;
    }

    public String getRootCmdClassName() {
        return rootCmdClassName;
    }

    public Long getOffset() {
        return offset;
    }

    public void setPayloadObject(Serializable payloadObject) {
        this.payloadObject = payloadObject;
    }

    public void setPayloadClassName(String payloadClassName) {
        this.payloadClassName = payloadClassName;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public void setParentMsgId(String parentMsgId) {
        this.parentMsgId = parentMsgId;
    }

    public void setRootCmdClassName(String rootCmdClassName) {
        this.rootCmdClassName = rootCmdClassName;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
