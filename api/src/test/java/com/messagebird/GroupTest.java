package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static org.junit.Assert.*;

public class GroupTest {

    private static final String JSON_GROUP_LIST = "{\"offset\": 0,\"limit\": 10,\"count\": 2,\"totalCount\": 2,\"links\": {\"first\": \"https://rest.messagebird.com/groups?offset=0&limit=10\",\"previous\": null,\"next\": null,\"last\": \"https://rest.messagebird.com/groups?offset=0&limit=10\"},\"items\": [{\"id\": \"first-id\",\"href\": \"https://rest.messagebird.com/groups/first-id\",\"name\": \"First\",\"contacts\": {\"totalCount\": 3,\"href\": \"https://rest.messagebird.com/groups/first-id/contacts\"},\"createdDatetime\": \"2018-07-25T11:47:42+00:00\",\"updatedDatetime\": \"2018-07-25T14:03:09+00:00\"},{\"id\": \"second-id\",\"href\": \"https://rest.messagebird.com/groups/second-id\",\"name\": \"Second\",\"contacts\": {\"totalCount\": 4,\"href\": \"https://rest.messagebird.com/groups/second-id/contacts\"},\"createdDatetime\": \"2018-07-25T11:47:39+00:00\",\"updatedDatetime\": \"2018-07-25T14:03:09+00:00\"}]}";
    private static final String JSON_GROUP = "{\"id\": \"group-id\",\"href\": \"https://rest.messagebird.com/groups/group-id\",\"name\": \"Friends\",\"contacts\": {\"totalCount\": 3,\"href\": \"https://rest.messagebird.com/groups/group-id\"},\"createdDatetime\": \"2018-07-25T12:16:10+00:00\",\"updatedDatetime\": \"2018-07-25T12:16:23+00:00\"}";

    @Test
    public void testDeleteGroup() throws GeneralException, NotFoundException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("DELETE", "groups/group-id")
                .withRestAPIBaseURL()
                .andReturns(new APIResponse("", HTTP_NO_CONTENT));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        messageBirdClient.deleteGroup("group-id");
    }

    @Test
    public void testDeleteGroupContact() throws UnauthorizedException, GeneralException, NotFoundException {
        MessageBirdService messageBirdService = SpyService
                .expects("DELETE", "groups/group-id/contacts/contact-id")
                .withRestAPIBaseURL()
                .andReturns(new APIResponse("", HTTP_NO_CONTENT));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        messageBirdClient.deleteGroupContact("group-id", "contact-id");
    }

    @Test
    public void testListGroups() throws UnauthorizedException, GeneralException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "groups?offset=0&limit=10")
                .withRestAPIBaseURL()
                .andReturns(new APIResponse(JSON_GROUP_LIST));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        GroupList groupList = messageBirdClient.listGroups(0, 10);

        assertEquals(Integer.valueOf(2), groupList.getTotalCount());
        assertEquals("https://rest.messagebird.com/groups?offset=0&limit=10", groupList.getLinks().getLast());
        assertEquals("Second", groupList.getItems().get(1).getName());
    }

    @Test
    public void testSendGroup() throws GeneralException, UnauthorizedException {
        GroupRequest groupRequest = new GroupRequest("Foo Group");

        MessageBirdService messageBirdService = SpyService
                .expects("POST", "groups", groupRequest)
                .withRestAPIBaseURL()
                .andReturns(new APIResponse(JSON_GROUP));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        messageBirdClient.sendGroup(groupRequest);
    }

    @Test
    public void testSendGroupContacts() throws UnauthorizedException, GeneralException, NotFoundException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "groups/group-id/contacts?_method=PUT&ids[]=foo&ids[]=bar")
                .withRestAPIBaseURL()
                .andReturns(new APIResponse("", HTTP_NO_CONTENT));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        messageBirdClient.sendGroupContact("group-id", new String[]{"foo", "bar"});
    }

    @Test
    public void testUpdateGroup() throws UnauthorizedException, GeneralException {
        GroupRequest  groupRequest = new GroupRequest("A different name");

        MessageBirdService messageBirdService = SpyService
                .expects("PATCH", "groups/group-id", groupRequest)
                .withRestAPIBaseURL()
                .andReturns(new APIResponse("", HTTP_NO_CONTENT));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        messageBirdClient.updateGroup("group-id", groupRequest);
    }

    @Test
    public void testViewGroup() throws UnauthorizedException, GeneralException, NotFoundException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "groups/group-id")
                .withRestAPIBaseURL()
                .andReturns(new APIResponse(JSON_GROUP));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        Group group = messageBirdClient.viewGroup("group-id");

        assertEquals("group-id", group.getId());
        assertEquals(3, group.getContacts().getTotalCount());
    }
}
