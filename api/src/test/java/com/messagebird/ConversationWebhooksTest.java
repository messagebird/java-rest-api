package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.ConversationWebhook;
import com.messagebird.objects.conversations.ConversationWebhookEvent;
import com.messagebird.objects.conversations.ConversationWebhookList;
import com.messagebird.objects.conversations.ConversationWebhookRequest;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ConversationWebhooksTest {

    private static final String EMPTY_RESPONSE_BODY = "";
    private static final String JSON_WEBHOOK = "{\"id\": \"whid\",\"url\": \"https://example.com/webhooks\",\"channelId\": \"chanid\",\"events\": [\"message.created\"],\"createdDatetime\": \"2018-08-30T09:34:36Z\",\"updatedDatetime\": null}";
    private static final String JSON_WEBHOOK_LIST = "{\"offset\": 10,\"limit\": 5,\"count\": 1,\"totalCount\": 11,\"items\": [{\"id\": \"whid\",\"url\": \"https://example.com/webhooks\",\"channelId\": \"chanid\",\"events\": [\"message.created\"],\"createdDatetime\": \"2018-08-24T14:46:39Z\",\"updatedDatetime\": null}]}";

    private static final int STATUS_NO_CONTENT = 204;

    @Test
    public void testDeleteConversationWebhook() throws GeneralException, NotFoundException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("DELETE", "webhooks/whid")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(EMPTY_RESPONSE_BODY, STATUS_NO_CONTENT));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);


        messageBirdClient.deleteConversationWebhook("whid");
    }

    @Test
    public void testListConversationWebhooks() throws GeneralException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "webhooks?offset=10&limit=5")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_WEBHOOK_LIST));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        ConversationWebhookList conversationWebhookList
                = messageBirdClient.listConversationWebhooks(10, 5);

        assertEquals(Integer.valueOf(10), conversationWebhookList.getOffset());
        assertEquals(Integer.valueOf(5), conversationWebhookList.getLimit());
        assertEquals(Integer.valueOf(11), conversationWebhookList.getTotalCount());
        assertEquals("whid", conversationWebhookList.getItems().get(0).getId());
    }

    @Test
    public void testSendConversationWebhook() throws GeneralException, UnauthorizedException {
        ConversationWebhookRequest request = new ConversationWebhookRequest(
                "chanid",
                "https://example.com/webhooks",
                Arrays.asList(
                        ConversationWebhookEvent.CONVERSATION_CREATED,
                        ConversationWebhookEvent.MESSAGE_CREATED
                )
        );

        MessageBirdService messageBirdService = SpyService
                .expects("POST", "webhooks", request)
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_WEBHOOK));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        ConversationWebhook conversationWebhook = messageBirdClient.sendConversationWebhook(request);

        assertEquals("whid", conversationWebhook.getId());
    }

    @Test
    public void testViewConversationWebhook() throws GeneralException, NotFoundException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "webhooks/whid")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_WEBHOOK));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        ConversationWebhook conversationWebhook = messageBirdClient.viewConversationWebhook("whid");

        assertEquals("whid", conversationWebhook.getId());
        assertEquals("https://example.com/webhooks", conversationWebhook.getUrl());
        assertEquals("chanid", conversationWebhook.getChannelId());
        assertEquals(1, conversationWebhook.getEvents().size());
        assertEquals(ConversationWebhookEvent.MESSAGE_CREATED, conversationWebhook.getEvents().get(0));
    }
}
