package id.aseprojali.social.rest.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by avew on 9/11/17.
 */
public class SubscriberDTO {

    @SerializedName("requestor")
    @Expose
    private String requestor;

    @SerializedName("target")
    @Expose
    private String target;

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "SubscriberDTO{" +
                "requestor='" + requestor + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
