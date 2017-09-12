package id.aseprojali.social.service;

import id.aseprojali.social.Constant;
import id.aseprojali.social.domain.Friend;
import id.aseprojali.social.domain.util.Util;
import id.aseprojali.social.exception.FriendException;
import id.aseprojali.social.repository.FriendRepository;
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


    public Map<String, Object> createAccountAndAddConnections(List<String> friends) throws FriendException {

        Map<String, Object> map = new HashMap<>();

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
            map.put(Constant.SUCCESS, true);

        } catch (Exception ex) {
            throw new FriendException(ex.getMessage());
        }
        return map;
    }


    public Map<String, Object> retrieveFriends(String email) throws FriendException {

        Friend friend = friendRepository.findOne(email);

        Map<String, Object> map = new HashMap<>();

        if (friend != null) {
            map.put(Constant.SUCCESS, true);
            map.put(Constant.FRIENDS, friend.getConnections());
            map.put(Constant.COUNT, friend.getConnections().size());

        } else {
            throw new FriendException("Friend doesn't exist");
        }
        return map;
    }


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


    public Map<String, Object> addSubsciber(String requestor, String target) throws FriendException {

        Friend friend = friendRepository.findOne(target);

        Map<String, Object> map = new HashMap<>();

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
            throw new FriendException("Friend doesn't exist");
        }

        return map;
    }


    public Map<String, Object> addBlockEmail(String requestor, String target) throws FriendException {
        Friend friend = friendRepository.findOne(target);

        Map<String, Object> map = new HashMap<>();

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
            throw new FriendException("Friend doesn't exist");
        }
        return map;
    }


    public Map<String, Object> retrieveAllEmailAddressThatCanReceiveUpdate(String sender, String text) throws FriendException {

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
            throw new FriendException("Friend doesn't exist");
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
