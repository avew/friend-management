package id.aseprojali.friendmanagement.service;

import id.aseprojali.friendmanagement.Constant;
import id.aseprojali.friendmanagement.domain.Friend;
import id.aseprojali.friendmanagement.util.Util;
import id.aseprojali.friendmanagement.exception.NotFoundException;
import id.aseprojali.friendmanagement.repository.FriendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by avew on 9/12/17.
 */
@Slf4j
@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;


    /**
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
     * @param friends
     * @return
     */
    public Map<String, Object> createAccountAndAddConnections(List<String> friends) {

        Map<String, Object> map = new HashMap<>();


        friends.forEach(email -> {

            int indexOf = friends.indexOf(email);

            int connection = indexOf == 0 ? 1 : 0;

            Friend person = friendRepository.findOne(friends.get(indexOf));

            if (person == null) {
                Friend friend = Friend.builder()
                        .email(friends.get(indexOf))
                        .connections(Collections.singletonList(friends.get(connection)))
                        .build();

                friendRepository.save(friend);
            } else {

                if (person.getConnections().indexOf(friends.get(connection)) == -1) {
                    person.getConnections().add(friends.get(connection));
                }

                person.setEmail(friends.get(indexOf));
                person.getConnections().add(friends.get(connection));
                friendRepository.save(person);

            }

        });

        map.put(Constant.SUCCESS, true);

        return map;
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
     *
     * @param email string
     * @return map
     * @throws NotFoundException
     */
    public Map<String, Object> retrieveFriends(String email) throws NotFoundException {

        Map<String, Object> map = new HashMap<>();

        Friend friend = friendRepository.findOne(email);

        if (friend != null) {
            map.put(Constant.SUCCESS, true);
            map.put(Constant.FRIENDS, friend.getConnections());
            map.put(Constant.COUNT, friend.getConnections().size());

        } else {
            throw new NotFoundException("friend doesn't exist");
        }

        return map;
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
     * Please propose JSON responses for any errors that might occur.
     *
     * @param emails
     * @return
     */
    public Map<String, Object> retrieveCommonFriends(List<String> emails) {

        Map<String, Object> map = new HashMap<>();

        List<String> common = new ArrayList<>();

        List<Friend> commonFriendList = friendRepository.findAllByEmail(emails);

        if (commonFriendList.size() > 0) {

            common = commonFriendList.stream()
                    .map(Friend::getConnections)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList())
                    .stream().collect(Collectors.groupingBy(email -> email, Collectors.counting())).entrySet()
                    .stream().filter(stringLongEntry -> stringLongEntry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }


        map.put(Constant.SUCCESS, true);
        map.put(Constant.FRIENDS, common);
        map.put(Constant.COUNT, common.size());

        return map;

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
     *
     * @param requestor
     * @param target
     * @return
     * @throws NotFoundException
     */
    public Map<String, Object> addSubsciber(String requestor, String target) throws NotFoundException {

        Map<String, Object> map = new HashMap<>();

        Friend friend = friendRepository.findOne(target);

        if (friend != null) {

            List<String> subscibers = Optional.ofNullable(friend.getSubscibers()).orElseGet(ArrayList::new);

            if (subscibers.indexOf(target) == -1) {
                subscibers.add(requestor);
            }

            friend.setEmail(target);
            friend.setSubscibers(subscibers);
            friendRepository.save(friend);

            map.put(Constant.SUCCESS, true);

        } else {
            throw new NotFoundException("Friend doesn't exist");
        }

        return map;
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
     *
     * @param requestor
     * @param target
     * @return
     * @throws NotFoundException
     */
    public Map<String, Object> addBlockEmail(String requestor, String target) throws NotFoundException {

        Map<String, Object> map = new HashMap<>();

        Friend friend = friendRepository.findOne(target);

        if (friend != null) {

            List<String> blockeds = Optional.ofNullable(friend.getBlockeds()).orElseGet(ArrayList::new);

            if (blockeds.indexOf(target) == -1) {
                blockeds.add(requestor);
            }

            friend.setEmail(target);
            friend.setBlockeds(blockeds);
            friendRepository.save(friend);

            map.put(Constant.SUCCESS, true);

        } else {
            throw new NotFoundException("Friend doesn't exist");
        }
        return map;
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
     *
     * @param sender
     * @param text
     * @return
     * @throws NotFoundException
     */
    public Map<String, Object> retrieveAllEmailAddressThatCanReceiveUpdate(String sender, String text) throws NotFoundException {

        Map<String, Object> map = new HashMap<>();

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

            map.put(Constant.SUCCESS, true);
            map.put(Constant.RECIPIENTS, personCanReceiveUpdates);

        } else {
            throw new NotFoundException("Friend doesn't exist");
        }

        return map;

    }


    public Friend findOneByEmail(String email) {
        return friendRepository.findOne(email);
    }

    public void deleteByEmail(String email) {
        friendRepository.delete(email);
    }
}
