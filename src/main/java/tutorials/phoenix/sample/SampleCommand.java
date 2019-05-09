package tutorials.phoenix.sample;

import tutorials.phoenix.message.Command;

/**
 * Created by gchaoxue on 2018/12/13
 */
public class SampleCommand extends Command {

    // the businessId is defining as the aggregatorId
    private String businessId;

    private String message;
    private Integer businessValue;

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
}
