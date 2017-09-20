package id.aseprojali.friendmanagement.repository;

import id.aseprojali.friendmanagement.domain.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by avew on 9/12/17.
 */
public interface FriendRepository extends MongoRepository<Friend, String>, FriendRepositoryCustom {
}
