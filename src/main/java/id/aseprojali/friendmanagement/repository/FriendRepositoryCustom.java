package id.aseprojali.friendmanagement.repository;

import id.aseprojali.friendmanagement.domain.Friend;

import java.util.List;

/**
 * Created by avew on 9/12/17.
 */
public interface FriendRepositoryCustom {

    List<Friend> findAllByEmail(List<String> emails);

}
