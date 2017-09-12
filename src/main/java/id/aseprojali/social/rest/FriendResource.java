package id.aseprojali.social.rest;

import id.aseprojali.social.domain.Friend;
import id.aseprojali.social.rest.dto.FriendsDTO;
import id.aseprojali.social.rest.dto.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avew on 9/11/17.
 */
@RestController
@Slf4j
public class FriendResource {

    private List<Friend> accounts = new ArrayList<>();

    @RequestMapping(
            value = "/api/create/friends",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createAccounts(@RequestBody FriendsDTO friendsDTO) {

        if (friendsDTO.getFriends() != null) {

//            friendsDTO.getFriends().forEach(email -> {
//                log.debug("email " + email);
//
//                accounts.forEach(account -> {
//                    if (!account.getEmail().equalsIgnoreCase(email)) {
//                        accounts.add(Account.builder().email(email).build());
//                    }
//                });
//
//            });

            MessageResponse success = MessageResponse.builder().status("success").build();
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } else {
            MessageResponse error = MessageResponse.builder().status("failed").build();
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
