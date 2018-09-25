package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConversationsTest {

    private static final String JSON_CONVERSATION = "{\"id\": \"convid\",\"contactId\": \"contid\",\"contact\": {\"id\": \"contid\",\"href\": \"https://chat.messagebird.com/1/contacts/contid\",\"msisdn\": 31612345678,\"firstName\": \"Foo\",\"lastName\": \"Bar\",\"customDetails\": {\"avatar\": \"https://example.com/assets/image.jpg\",\"firstName\": \"Foo\",\"lastName\": \"Bar\",\"userId\": 12345678 },\"createdDatetime\": \"2018-08-22T15:47:32Z\",\"updatedDatetime\": null},\"channels\": [{\"id\": \"chid\",\"name\": \"chname\",\"platformId\": \"telegram\",\"status\": \"active\",\"createdDatetime\": \"2018-08-22T15:18:11Z\",\"updatedDatetime\": \"2018-08-22T15:18:13Z\"}],\"status\": \"active\",\"createdDatetime\": \"2018-08-22T15:47:34Z\",\"updatedDatetime\": \"2018-08-22T16:05:15Z\",\"lastReceivedDatetime\": \"2018-08-22T15:47:34Z\",\"lastUsedChannelId\": \"chid\",\"messages\": {\"totalCount\": 1,\"href\": \"https://conversations.messagebird.com/v1/conversations/convid/messages\"}}";
    private static final String JSON_CONVERSATION_LIST = "{\"offset\": 20,\"limit\": 10,\"count\": 1,\"totalCount\": 1,\"items\": [{\"id\": \"convid\",\"contactId\": \"contid\",\"contact\": {\"id\": \"contid\",\"href\": \"https://chat.messagebird.com/1/contacts/contid\",\"msisdn\": 31612345678,\"firstName\": \"Foo\",\"lastName\": \"Bar\",\"customDetails\": {\"avatar\": \"https://s3-eu-west-1.amazonaws.com/messagebird-chat/telegram/0d1dae7t5b7d7eb4531c14n04328336/6de655at5b7d859309f821n60607065/d0b705dt5b7d859309f987n05372263.jpg\",\"firstName\": \"Foo\",\"lastName\": \"Bar\",\"userId\": 12345678},\"createdDatetime\": \"2018-08-22T15:47:32Z\",\"updatedDatetime\": null},\"channels\": [{\"id\": \"chid\",\"name\": \"TestChannel\",\"platformId\": \"telegram\",\"status\": \"active\",\"createdDatetime\": \"2018-08-22T15:18:11Z\",\"updatedDatetime\": \"2018-08-22T15:18:13Z\"}],\"status\": \"active\",\"createdDatetime\": \"2018-08-22T15:47:34Z\",\"updatedDatetime\": \"2018-08-24T09:49:01Z\",\"lastReceivedDatetime\": \"2018-08-24T09:49:01Z\",\"lastUsedChannelId\": \"chid\",\"messages\": {\"totalCount\": 3,\"href\": \"https://conversations.messagebird.com/v1/conversations/convid/messages\"}}]}";
    private static final String JSON_UNAUTHORIZED_ERROR = "{\"errors\": [{\"code\": 2,\"description\": \"Request was not authenticated\"}]}";

    @Test(expected = UnauthorizedException.class)
    public void testItThrowsErrors() throws GeneralException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "conversations?offset=0&limit=10")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_UNAUTHORIZED_ERROR, 401));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        messageBirdClient.listConversations();
    }

    @Test
    public void testListConversations() throws GeneralException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "conversations?offset=20&limit=10")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_CONVERSATION_LIST));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        ConversationList conversationList = messageBirdClient.listConversations(20, 10);

        assertEquals(Integer.valueOf(20), conversationList.getOffset());
        assertEquals("convid", conversationList.getItems().get(0).getId());
    }

    @Test
    public void testStartConversation() throws GeneralException, UnauthorizedException {
        ConversationContent conversationContent = new ConversationContent();
        conversationContent.setText("Hello world");

        ConversationStartRequest request = new ConversationStartRequest(
                "31612345678",
                ConversationContentType.TEXT,
                conversationContent,
                "chanid"
        );

        MessageBirdService messageBirdService = SpyService
                .expects("POST", "conversations/start", request)
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_CONVERSATION));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        Conversation conversation = messageBirdClient.startConversation(request);

        assertEquals("convid", conversation.getId());
    }

    @Test
    public void testUpdateConversation() throws GeneralException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("PATCH", "conversations/convid", ConversationStatus.ARCHIVED)
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_CONVERSATION));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        Conversation conversation = messageBirdClient.updateConversation("convid", ConversationStatus.ARCHIVED);

        assertEquals("convid", conversation.getId());
    }

    @Test
    public void testViewConversation() throws GeneralException, UnauthorizedException, NotFoundException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "conversations/convid")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_CONVERSATION));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        Conversation conversation = messageBirdClient.viewConversation("convid");

        assertEquals("convid", conversation.getId());
        assertEquals(ConversationStatus.ACTIVE, conversation.getStatus());
        assertEquals(ConversationChannelStatus.ACTIVE, conversation.getChannels().get(0).getStatus());
        assertEquals("contid", conversation.getContact().getId());
        assertEquals("Foo", conversation.getContact().getCustomDetails().get("firstName"));
        assertEquals(1, conversation.getMessages().getTotalCount());
    }
}
