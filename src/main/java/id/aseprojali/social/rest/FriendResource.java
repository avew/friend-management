package id.aseprojali.social.rest;

import id.aseprojali.social.domain.Account;
import id.aseprojali.social.repository.AccountRepository;
import id.aseprojali.social.rest.dto.FriendsDTO;
import id.aseprojali.social.rest.dto.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(
            value = "/api/friends",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createAccounts(@RequestBody FriendsDTO friendsDTO) {

        if (friendsDTO.getFriends() != null) {

            friendsDTO.getFriends().forEach(email -> {
                log.debug("email " + email);

                if (!accountRepository.findByEmail(email).isPresent()) {
                    Account accountSaved = accountRepository.save(Account.builder().email(email).build());
                    log.debug("saved email " + accountSaved.getEmail());
                }

            });

            MessageResponse success = MessageResponse.builder().status("success").build();
            return new ResponseEntity<>(success, HttpStatus.OK);
        } else {
            MessageResponse error = MessageResponse.builder().status("failed").build();
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
