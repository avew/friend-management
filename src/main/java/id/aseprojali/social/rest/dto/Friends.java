package id.aseprojali.social.rest.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by avew on 9/11/17.
 */
public class Friends {

    @SerializedName("friends")
    @Expose
    private List<String> friends = null;

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }


}
