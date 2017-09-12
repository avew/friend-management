package id.aseprojali.social.repository;

import id.aseprojali.social.domain.Friend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by avew on 9/12/17.
 */
@Repository
@Slf4j
public class FriendRepositoryImpl implements FriendRepositoryCustom {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<Friend> findAllByEmail(List<String> emails) {
        Query q = new Query();
        q.addCriteria(Criteria.where("email").in(emails));
        return mongoOperations.find(q, Friend.class);
    }
}
