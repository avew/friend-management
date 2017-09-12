package id.aseprojali.social.service.account;

import id.aseprojali.social.domain.Friend;
import id.aseprojali.social.domain.util.Util;
import id.aseprojali.social.repository.FriendRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by avew on 9/11/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DataMongoTest
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FriendServiceTest {

    private List<String> friends = new ArrayList<>();


    @Autowired
    private FriendRepository friendRepository;


    @Before
    public void before() {
        friends.add("andy@example.com");
        friends.add("john@example.com");
    }

    /**
     * User Stories
     * 1. As a user, I need an API to create a friend connection between two email addresses.
     * The API should receive the following JSON request:
     * {
     * friends:
     * [
     * 'andy@example.com',
     * 'john@example.com'
     * ]
     * }
     * The API should return the following JSON response on success:
     * {
     * "success": true
     * }
     * Please propose JSON responses for any errors that might occur.
     *
     * @throws Exception
     */
    @Test
    public void aTestCreateAccountAndAddFriends() throws Exception {

        friendRepository.deleteAll();

        try {

            friends.forEach(email -> {
                int indexOf = friends.indexOf(email);
                int connection = indexOf == 0 ? 1 : 0;

                Friend person = friendRepository.findOne(friends.get(indexOf));
                if (person == null) {
                    friendRepository.save(Friend.builder().email(friends.get(indexOf)).connections(Collections.singletonList(friends.get(connection))).build());
                } else {
                    if (person.getConnections().indexOf(friends.get(connection)) == -1) {
                        person.getConnections().add(friends.get(connection));
                    }

                    person.setEmail(friends.get(indexOf));
                    person.getConnections().add(friends.get(connection));
                    friendRepository.save(person);

                }

            });

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        Friend andy = friendRepository.findOne("andy@example.com");
        Assert.assertEquals(andy.getEmail(), "andy@example.com");
        Assert.assertEquals(andy.getConnections().get(0), "john@example.com");
        Assert.assertEquals(andy.getConnections().size(), 1);

    }

    /**
     * 2. As a user, I need an API to retrieve the friends list for an email address.
     * The API should receive the following JSON request:
     * {
     * <p>
     * email: 'andy@example.com'
     * }
     * The API should return the following JSON response on success:
     * {
     * "success": true,
     * "friends" :
     * [
     * 'john@example.com'
     * ],
     * "count" : 1
     * }
     * Please propose JSON responses for any errors that might occur.
     */
    @Test
    public void bTestRetrieveFriend() {

        String email = "andy@example.com";

        Friend friend = friendRepository.findOne(email);

        Map<String, Object> map = new HashMap<>();

        if (friend != null) {
            map.put("success", true);
            map.put("friends", friend.getConnections());
            map.put("count", friend.getConnections().size());

        } else {
            map.put("success", true);
            map.put("friends", new ArrayList<>());
            map.put("count", 0);
        }

        Assert.assertTrue((Boolean) map.get("success"));
        Assert.assertEquals(map.get("count"), 1);
    }

    /**
     * 3. As a user, I need an API to retrieve the common friends list between two email
     * addresses.
     * The API should receive the following JSON request:
     * {
     * friends:
     * [
     * 'andy@example.com',
     * 'john@example.com'
     * ]
     * }
     * The API should return the following JSON response on success:
     * {
     * "success": true,
     * "friends" :
     * [
     * 'common@example.com'
     * ],
     * "count" : 1
     * }
     * Please propose JSON responses for any errors that might
     */
    @Test
    public void cTestRetrieveCommonFriendList() {

        List<Friend> commonFriendList = friendRepository.findAllByEmail(friends);
//        commonFriendList.forEach(x -> {
//            x.getConnections().add("common@example.com");
//            friendRepository.save(x);
//        });

        List<String> common = commonFriendList.stream()
                .map(Friend::getConnections)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .stream().collect(Collectors.groupingBy(email -> email, Collectors.counting())).entrySet()
                .stream().filter(stringLongEntry -> stringLongEntry.getValue() > 1)

                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


    }

    /**
     * 4. As a user, I need an API to subscribe to updates from an email address.
     * Please note that "subscribing to updates" is NOT equivalent to "adding a friend connection".
     * The API should receive the following JSON request:
     * {
     * "requestor": "lisa@example.com",
     * "target": "john@example.com"
     * }
     * The API should return the following JSON response on success:
     * {
     * "success": true
     * }
     * Please propose JSON responses for any errors that might occur.
     */
    @Test
    public void dTestRetrieveSubscribeToUpdateFromAnEmailAddress() {

        String requestor = "lisa@example.com";
        String target = "john@example.com";

        Friend friend = friendRepository.findOne(target);

        if (friend != null) {

            List<String> followers = Optional.ofNullable(friend.getSubscibers()).orElseGet(ArrayList::new);

            if (followers.indexOf(target) == -1) {
                followers.add(requestor);
            }

            friend.setEmail(target);
            friend.setSubscibers(followers);
            Friend save = friendRepository.save(friend);

            Assert.assertEquals(save.getSubscibers().get(0), requestor);
        }


    }

    /**
     * 5. As a user, I need an API to block updates from an email address.
     * <p>
     * Suppose "andy@example.com" blocks "john@example.com":
     * • if they are connected as friends, then "andy" will no longer receive notifications from
     * "john"
     * • if they are not connected as friends, then no new friends connection can be added
     * The API should receive the following JSON request:
     * {
     * "requestor": "andy@example.com",
     * "target": "john@example.com"
     * }
     * The API should return the following JSON response on success:
     * {
     * "success": true
     * }
     * Please propose JSON responses for any errors that might occur.
     */
    @Test
    public void eTestRetrieveBlockUpdateFromAnEmailAddress() {

        String requestor = "andy@example.com";
        String target = "john@example.com";

        Friend friend = friendRepository.findOne(target);

        if (friend != null) {

            List<String> blockeds = Optional.ofNullable(friend.getBlockeds()).orElseGet(ArrayList::new);

            if (blockeds.indexOf(target) == -1) {
                blockeds.add(requestor);
            }

            friend.setEmail(target);
            friend.setBlockeds(blockeds);
            Friend save = friendRepository.save(friend);

            Assert.assertEquals(save.getBlockeds().get(0), requestor);

        }
    }

    /**
     * 6. As a user, I need an API to retrieve all email addresses that can receive updates from an
     * email address.
     * Eligibility for receiving updates from i.e. "john@example.com":
     * • has not blocked updates from "john@example.com", and
     * • at least one of the following:
     * o has a friend connection with "john@example.com"
     * o has subscribed to updates from "john@example.com"
     * o has been @mentioned in the update
     * The API should receive the following JSON request:
     * {
     * "sender": "john@example.com",
     * "text": "Hello World! kate@example.com"
     * }
     * The API should return the following JSON response on success:
     * {
     * "success": true
     * "recipients":
     * [
     * "lisa@example.com",
     * "kate@example.com"
     * ]
     * }
     * Please propose JSON responses for any errors that might occur.
     */
    @Test
    public void fTestRetrieveAllEmailAddressThatCanReceiveUpdate() {


        String sender = "john@example.com";
        String text = "Hello World! kate@example.com";

        Friend friend = friendRepository.findOne(sender);

        List<String> personCanReceiveUpdates = new ArrayList<>();

        if (friend != null) {

            if (friend.getConnections().size() > 0) personCanReceiveUpdates.addAll(friend.getConnections());

            if (friend.getSubscibers().size() > 0) personCanReceiveUpdates.addAll(friend.getSubscibers());

            if (friend.getBlockeds().size() > 0) {
                friend.getBlockeds().forEach(email -> {
                    if (personCanReceiveUpdates.contains(email)) {
                        personCanReceiveUpdates.remove(email);
                    }
                });
            }

            String mentionFromWords = Util.getMentionFromWords(text);
            if (mentionFromWords != null) personCanReceiveUpdates.add(mentionFromWords);

        }

        Assert.assertEquals(personCanReceiveUpdates.size(), 2);
        Assert.assertEquals(personCanReceiveUpdates.get(0), "lisa@example.com");
        Assert.assertEquals(personCanReceiveUpdates.get(1), "kate@example.com");

    }


}