package tutorials.phoenix.sample;

import tutorials.phoenix.message.Event;

/**
 * Created by gchaoxue on 2018/12/13
 */
public class SampleEvent extends Event {

    private String businessId;

    private String message;
    private Integer businessValue;
    private Integer businessStatus;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(Integer businessValue) {
        this.businessValue = businessValue;
    }

    public Integer getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(Integer businessStatus) {
        this.businessStatus = businessStatus;
    }
}
