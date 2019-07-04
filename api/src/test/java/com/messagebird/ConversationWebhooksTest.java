package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;

import static com.messagebird.TestUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConversationWebhooksTest {
    private static final String WHID = "whid";
    private static final String CONVERSATIONS_WEBHOOK_URL = "https://conversations.messagebird.com/v1/webhooks";
    private static final String CHANID = "chanid";
    private static final String HTTPS_EXAMPLE_COM_WEBHOOKS = "https://example.com/webhooks";


    private MessageBirdService mockMessageBirdService;
    private MessageBirdClient messageBirdClient;

    @Before
    public void setUp() {
        mockMessageBirdService = mock(MessageBirdService.class);
        messageBirdClient = new MessageBirdClient(mockMessageBirdService);
    }

    @Test
    public void testDeleteConversationWebhook() throws GeneralException, NotFoundException, UnauthorizedException {
        doNothing().when(mockMessageBirdService).deleteByID(HTTPS_EXAMPLE_COM_WEBHOOKS, WHID);

        messageBirdClient.deleteConversationWebhook(WHID);

        verify(mockMessageBirdService, times(1)).deleteByID(CONVERSATIONS_WEBHOOK_URL, WHID);
    }

    @Test
    public void testListConversationWebhooks() throws GeneralException, UnauthorizedException {
        ConversationWebhookList conversationWebhookListResponse = mock(ConversationWebhookList.class);
        ConversationWebhook conversationWebhook = new ConversationWebhook();
        conversationWebhook.setId(WHID);
        when(conversationWebhookListResponse.getItems()).thenReturn(Collections.singletonList(conversationWebhook));
        when(conversationWebhookListResponse.getLimit()).thenReturn(5);
        when(conversationWebhookListResponse.getOffset()).thenReturn(10);
        when(conversationWebhookListResponse.getTotalCount()).thenReturn(11);
        when(mockMessageBirdService.requestList(CONVERSATIONS_WEBHOOK_URL, 10, 5, ConversationWebhookList.class))
                .thenReturn(conversationWebhookListResponse);

        ConversationWebhookList conversationWebhookList
                = messageBirdClient.listConversationWebhooks(10, 5);

        assertEquals(Integer.valueOf(10), conversationWebhookList.getOffset());
        assertEquals(Integer.valueOf(5), conversationWebhookList.getLimit());
        assertEquals(Integer.valueOf(11), conversationWebhookList.getTotalCount());
        assertEquals(WHID, conversationWebhookList.getItems().get(0).getId());
    }

    @Test
    public void testSendConversationWebhook() throws GeneralException, UnauthorizedException {
        ConversationWebhookCreateRequest request = createConversationWebhookRequest();

        ConversationWebhook conversationWebhookResponse = new ConversationWebhook();
        conversationWebhookResponse.setId(WHID);

        when(mockMessageBirdService.sendPayLoad(CONVERSATIONS_WEBHOOK_URL, request, ConversationWebhook.class))
                .thenReturn(conversationWebhookResponse);

        ConversationWebhook conversationWebhook = messageBirdClient.sendConversationWebhook(request);

        assertEquals(WHID, conversationWebhook.getId());
    }

    @Test
    public void testUpdateConversationWebhook() throws GeneralException, UnauthorizedException {
        ConversationWebhookUpdateRequest request = createConversationWebhookUpdateRequest();

        ConversationWebhook conversationWebhookResponse = new ConversationWebhook();
        conversationWebhookResponse.setId(WHID);
        conversationWebhookResponse.setUpdatedDatetime(new Date());

        when(mockMessageBirdService.sendPayLoad("PATCH",CONVERSATIONS_WEBHOOK_URL + "/chanid", request, ConversationWebhook.class))
                .thenReturn(conversationWebhookResponse);

        ConversationWebhook conversationWebhook = messageBirdClient.updateConversationWebhook(CHANID, request);

        assertEquals(WHID, conversationWebhook.getId());
        assertNotNull(conversationWebhook.getUpdatedDatetime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateConversationWebhookWithNullWebhookId() throws GeneralException, UnauthorizedException {
        ConversationWebhookUpdateRequest request = createConversationWebhookUpdateRequest();

        messageBirdClient = new MessageBirdClient(null);

        messageBirdClient.updateConversationWebhook(null, request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateConversationWebhookWithEmptyWebhookId() throws GeneralException, UnauthorizedException {
        ConversationWebhookUpdateRequest request = createConversationWebhookUpdateRequest();

        messageBirdClient = new MessageBirdClient(null);

        messageBirdClient.updateConversationWebhook("", request);
    }

    @Test
    public void testViewConversationWebhook() throws GeneralException, NotFoundException, UnauthorizedException {
        ConversationWebhook conversationWebhookResponse = createConversationWebhook();
        when(mockMessageBirdService.requestByID(CONVERSATIONS_WEBHOOK_URL, WHID, ConversationWebhook.class))
                .thenReturn(conversationWebhookResponse);

        ConversationWebhook conversationWebhook = messageBirdClient.viewConversationWebhook(WHID);

        assertEquals(WHID, conversationWebhook.getId());
        assertEquals(HTTPS_EXAMPLE_COM_WEBHOOKS, conversationWebhook.getUrl());
        assertEquals(CHANID, conversationWebhook.getChannelId());
        assertEquals(1, conversationWebhook.getEvents().size());
        assertEquals(ConversationWebhookEvent.MESSAGE_CREATED, conversationWebhook.getEvents().get(0));
    }
}
