package id.aseprojali.social.repository;

import id.aseprojali.social.domain.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by avew on 9/12/17.
 */
public interface FriendRepository extends MongoRepository<Friend, String>,FriendRepositoryCustom {
}
