package id.aseprojali.social.rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import id.aseprojali.social.Constant;
import id.aseprojali.social.rest.dto.FriendsDTO;
import id.aseprojali.social.rest.dto.SubscriberDTO;
import id.aseprojali.social.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by avew on 9/13/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class FriendResourceTest {


    @LocalServerPort
    int serverPort;

    @Autowired
    private FriendService friendService;

    private static final String first = "andy@example.com";

    private static final String second = "john@example.com";


    @Before
    public void setup() {
        RestAssured.port = serverPort;
    }

    @Test
    public void aTestPostCreateAccounts() throws Exception {

        FriendsDTO bodyValid = new FriendsDTO();
        bodyValid.setFriends(Arrays.asList(first, second));

        given()
                .body(bodyValid)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/friends")
                .then()
                .statusCode(201)
                .body(Constant.SUCCESS, equalTo(true));


        FriendsDTO bodyInvalid = new FriendsDTO();
        bodyInvalid.setFriends(Collections.singletonList(first));

        given()
                .body(bodyInvalid)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/friends")
                .then()
                .statusCode(400)
                .body(Constant.SUCCESS, equalTo(false));
    }

    @Test
    public void bTestGetRetrieveFriends() throws Exception {

        given()
                .param("email", first)
                .contentType(ContentType.URLENC)
                .when()
                .get("/api/friend")
                .then()
                .statusCode(200)
                .body(Constant.SUCCESS, equalTo(true));


        given()
                .param("email", "emailnotfound@example.com")
                .contentType(ContentType.URLENC)
                .when()
                .get("/api/friend")
                .then()
                .statusCode(404)
                .body(Constant.SUCCESS, equalTo(false));

    }

    @Test
    public void cTestpostGetCommonFriend() throws Exception {

        FriendsDTO bodyValid = new FriendsDTO();
        bodyValid.setFriends(Arrays.asList(first, second));

        given()
                .body(bodyValid)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/friends/common")
                .then()
                .statusCode(200)
                .body(Constant.SUCCESS, equalTo(true));
    }

    @Test
    public void dTestPostAddSubsciber() throws Exception {

        SubscriberDTO bodyValid = new SubscriberDTO();
        bodyValid.setRequestor("lisa@example.com");
        bodyValid.setTarget("john@example.com");

        given()
                .body(bodyValid)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/friend/subscribe")
                .then()
                .statusCode(200)
                .body(Constant.SUCCESS, equalTo(true));
    }

    @Test
    public void eTestDeletePerson() {
        friendService.deleteByEmail(first);
        friendService.deleteByEmail(second);
    }

}