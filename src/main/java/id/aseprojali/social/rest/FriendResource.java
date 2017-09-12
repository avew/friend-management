package id.aseprojali.social.rest;

import id.aseprojali.social.util.Util;
import id.aseprojali.social.exception.NotFoundException;
import id.aseprojali.social.exception.BadRequestException;
import id.aseprojali.social.rest.dto.FriendsDTO;
import id.aseprojali.social.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by avew on 9/11/17.
 */
@RestController
@Slf4j
public class FriendResource {

    @Autowired
    private FriendService friendService;

    @RequestMapping(
            value = "/api/friends",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> postCreateAccounts(@RequestBody FriendsDTO friends) throws BadRequestException {

        if (friends.getFriends() != null) {

            if (friends.getFriends().size() < 2) {
                throw new BadRequestException("please add 2 or more email");
            } else {
                Map<String, Object> map = friendService.createAccountAndAddConnections(friends.getFriends());
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

        } else {
            throw new BadRequestException("tag friends not found");
        }
    }

    @RequestMapping(
            value = "/api/friend",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getRetrieveFriends(@RequestParam(value = "email") String email) throws NotFoundException, UnsupportedEncodingException {
        log.debug("Request find email " + email);
        Map<String, Object> map = friendService.retrieveFriends(email);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
