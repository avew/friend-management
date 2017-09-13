package id.aseprojali.friendmanagement.repository;

import id.aseprojali.friendmanagement.domain.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by avew on 9/12/17.
 */
@Repository
public interface FriendRepository extends MongoRepository<Friend, String>,FriendRepositoryCustom {
}
