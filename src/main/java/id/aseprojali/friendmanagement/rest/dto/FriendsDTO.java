package id.aseprojali.friendmanagement.rest.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by avew on 9/11/17.
 */
public class FriendsDTO {

    @SerializedName("friends")
    @Expose
    private List<String> friends = null;

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }


    @Override
    public String toString() {
        return "FriendsDTO{" +
                "friends=" + friends +
                '}';
    }
}
