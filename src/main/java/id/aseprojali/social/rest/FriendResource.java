package id.aseprojali.social.rest;

import id.aseprojali.social.rest.dto.FriendsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by avew on 9/11/17.
 */
@RestController
@Slf4j
public class FriendResource {


    @RequestMapping(
            value = "/api/create/friends",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createAccounts(@RequestBody FriendsDTO friendsDTO) {
        return null;
    }
}
