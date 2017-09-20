package id.aseprojali.friendmanagement.service;

import id.aseprojali.friendmanagement.Constant;
import id.aseprojali.friendmanagement.FriendManagementApplication;
import id.aseprojali.friendmanagement.domain.Friend;
import id.aseprojali.friendmanagement.exception.NotFoundException;
import id.aseprojali.friendmanagement.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Created by avew on 9/11/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FriendManagementApplication.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FriendServiceTest {


    @Autowired
    private FriendService friendService;

    private static final String first = "andy@example.com";

    private static final String second = "john@example.com";

    private static final List<String> friends = new ArrayList<>();

    @Before
    public void before() {
        friends.add(first);
        friends.add(second);
    }


    @Test
    public void aTestCreateAccountAndAddFriends() throws Exception {

        Map<String, Object> map = friendService.createAccountAndAddConnections(friends);

        Friend andy = friendService.findOneByEmail(first);

        Assert.assertEquals(andy.getEmail(), first);
        Assert.assertEquals(andy.getConnections().get(0), second);
        Assert.assertEquals(andy.getConnections().size(), 1);
        Assert.assertTrue((Boolean) map.get(Constant.SUCCESS));

    }


    @Test
    public void bTestRetrieveFriend() throws NotFoundException {

        Map<String, Object> map = friendService.retrieveFriends(first);

        Assert.assertTrue((Boolean) map.get(Constant.SUCCESS));
        Assert.assertEquals(map.get(Constant.COUNT), 1);

        ArrayList<String> friends = (ArrayList<String>) map.get("friends");
        Assert.assertEquals(friends.get(0), second);
    }


    @Test
    public void cTestRetrieveCommonFriendList() {

        Map<String, Object> map = friendService.retrieveCommonFriends(friends);

        Assert.assertTrue((Boolean) map.get(Constant.SUCCESS));


    }


    @Test
    public void dTestAddSubsriber() throws NotFoundException {

        String requestor = "lisa@example.com";

        Map<String, Object> map = friendService.addSubsciber(requestor, second);

        Assert.assertTrue((Boolean) map.get(Constant.SUCCESS));

    }


    @Test
    public void eTestAddBlockEmail() throws NotFoundException {

        Map<String, Object> map = friendService.addBlockEmail(first, second);

        Assert.assertTrue((Boolean) map.get(Constant.SUCCESS));

    }


    @Test
    public void fTestRetrieveAllEmailAddressThatCanReceiveUpdate() throws NotFoundException {

        String text = "Hello World! kate@example.com";

        Map<String, Object> map = friendService.retrieveAllEmailAddressThatCanReceiveUpdate(second, text);

        Assert.assertTrue((Boolean) map.get(Constant.SUCCESS));


    }

    @Test
    public void gTestDeletePerson() {
        friendService.deleteByEmail(first);
        friendService.deleteByEmail(second);
    }


}