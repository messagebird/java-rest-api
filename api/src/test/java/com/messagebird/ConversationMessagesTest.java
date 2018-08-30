package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class ConversationMessagesTest {

    private static final String JSON_CONVERSATION_MESSAGE_LIST = "{\"count\": 1,\"items\": [{\"id\": \"mesid\",\"conversationId\": \"convid\",\"channelId\": \"chid\",\"status\": \"received\",\"type\": \"text\",\"direction\": \"received\",\"content\": {\"text\": \"Foo\"},\"createdDatetime\": \"2018-08-24T09:49:01Z\",\"updatedDatetime\": \"2018-08-24T09:49:01Z\"}],\"limit\": 10,\"offset\": 20,\"totalCount\": 1}";

    private static final String JSON_CONVERSATION_MESSAGE_AUDIO = "{\"id\": \"mesid\",\"conversationId\": \"convid\",\"channelId\": \"chanid\",\"status\": \"received\",\"type\": \"audio\",\"direction\": \"received\",\"content\": {\"audio\": { \"url\": \"https://example.com/audio.mp3\" } },\"createdDatetime\": \"2018-08-29T11:49:16Z\",\"updatedDatetime\": \"2018-08-29T11:49:16Z\"}";
    private static final String JSON_CONVERSATION_MESSAGE_HSM = "{\"id\": \"mesid\",\"conversationId\": \"convid\",\"channelId\": \"chanid\",\"status\": \"received\",\"type\": \"hsm\",\"direction\": \"received\",\"content\": {\"hsm\":{\"namespace\":\"ns\",\"templateName\":\"template\",\"language\":{\"policy\":\"deterministic\",\"code\":\"en_US\"},\"params\":[{\"default\":\"Hello!\"},{\"default\":\"EUR12.34\",\"currency\":{\"currencyCode\":\"EUR\",\"amount\":12340}},{\"default\":\"Today\",\"dateTime\":\"2018-08-24T11:52:12Z\"}]} },\"createdDatetime\": \"2018-08-29T11:49:16Z\",\"updatedDatetime\": \"2018-08-29T11:49:16Z\"}";
    private static final String JSON_CONVERSATION_MESSAGE_LOCATION = "{\"id\": \"mesid\",\"conversationId\": \"convid\",\"channelId\": \"chanid\",\"status\": \"received\",\"type\": \"location\",\"direction\": \"received\",\"content\": {\"location\": { \"latitude\": 52.344263, \"longitude\": 4.911627 } },\"createdDatetime\": \"2018-08-29T11:49:16Z\",\"updatedDatetime\": \"2018-08-29T11:49:16Z\"}";
    private static final String JSON_CONVERSATION_MESSAGE_TEXT = "{\"id\": \"mesid\",\"conversationId\": \"convid\",\"channelId\": \"chanid\",\"status\": \"received\",\"type\": \"text\",\"direction\": \"received\",\"content\": {\"text\": \"Hello\"},\"createdDatetime\": \"2018-08-29T11:49:16Z\",\"updatedDatetime\": \"2018-08-29T11:49:16Z\"}";
    private static final String JSON_CONVERSATION_MESSAGE_VIDEO = "{\"id\": \"mesid\",\"conversationId\": \"convid\",\"channelId\": \"chanid\",\"status\": \"received\",\"type\": \"video\",\"direction\": \"received\",\"content\": {\"video\": { \"url\": \"https://example.com/video.mp4\" } },\"createdDatetime\": \"2018-08-29T11:49:16Z\",\"updatedDatetime\": \"2018-08-29T11:49:16Z\"}";

    /**
     * Epsilon to use when checking two latitudes or longitudes for equality.
     */
    private static final double EPSILON_LOCATION_EQUALITY = 0.001;

    @Test
    public void testListConversationMessages() throws GeneralException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "conversations/convid/messages?offset=20&limit=10")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_CONVERSATION_MESSAGE_LIST));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        ConversationMessageList conversationMessageList
                = messageBirdClient.listConversationMessages("convid", 20, 10);

        assertEquals(Integer.valueOf(20), conversationMessageList.getOffset());
        assertEquals("mesid", conversationMessageList.getItems().get(0).getId());
    }

    @Test
    public void testSendConversationMessage() throws GeneralException, UnauthorizedException {
        ConversationContent conversationContent = new ConversationContent();
        conversationContent.setVideo(new ConversationContentMedia("https://example.com/video.mp4"));

        ConversationMessageRequest conversationMessageRequest = new ConversationMessageRequest();
        conversationMessageRequest.setChannelId("aChannelIdentifier");
        conversationMessageRequest.setType(ConversationContentType.VIDEO);
        conversationMessageRequest.setContent(conversationContent);

        MessageBirdService messageBirdService = SpyService
                .expects("POST", "conversations/convid/messages", conversationMessageRequest)
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_CONVERSATION_MESSAGE_VIDEO));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        ConversationMessage conversationMessage
                = messageBirdClient.sendConversationMessage("convid", conversationMessageRequest);

        assertEquals(ConversationContentType.VIDEO, conversationMessage.getType());
    }

    @Test
    public void testViewConversationMessageAudio() throws GeneralException, NotFoundException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "messages/mesid")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_CONVERSATION_MESSAGE_AUDIO));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        ConversationMessage conversationMessage
                = messageBirdClient.viewConversationMessage("mesid");

        assertEquals("chanid", conversationMessage.getChannelId());
        assertEquals("https://example.com/audio.mp3", conversationMessage.getContent().getAudio().getUrl());
    }

    @Test
    public void testViewConversationMessageHsm() throws GeneralException, NotFoundException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "messages/another-mesid")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_CONVERSATION_MESSAGE_HSM));
        MessageBirdClient client = new MessageBirdClient(messageBirdService);

        ConversationMessage conversationMessage = client.viewConversationMessage("another-mesid");

        ConversationContentHsm hsm = conversationMessage.getContent().getHsm();
        ConversationHsmLanguage language = hsm.getLanguage();
        List<ConversationHsmLocalizableParameter> params = hsm.getParams();

        assertEquals(ConversationContentType.HSM, conversationMessage.getType());
        assertEquals("ns", hsm.getNamespace());
        assertEquals("template", hsm.getTemplateName());
        assertEquals("en_US", language.getCode());
        assertEquals(ConversationHsmLanguagePolicy.DETERMINISTIC, language.getPolicy());

        assertEquals("Hello!", params.get(0).getDefaultValue());

        assertEquals("EUR12.34", params.get(1).getDefaultValue());
        assertEquals("EUR", params.get(1).getCurrency().getCurrencyCode());
        assertEquals(12340, params.get(1).getCurrency().getAmount());

        assertEquals("Today", params.get(2).getDefaultValue());
        assertEquals(getExpectedDateForHsm().getTime(), params.get(2).getDateTime().getTime());
    }

    /**
     * Constructs a Date that should equal the localizable date.
     *
     * @return UTC Date.
     */
    private Date getExpectedDateForHsm() {
        // The API returns time in RFC 3339 format, so it uses UTC. See:
        // https://www.ietf.org/rfc/rfc3339.txt
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);

        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, Calendar.AUGUST);
        calendar.set(Calendar.DAY_OF_MONTH, 24);
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 52);
        calendar.set(Calendar.SECOND, 12);

        // Truncate the milliseconds. Calendar.getInstance() returns a Calendar
        // that has been initialized with that instant's date and time: so if
        // we don't set the milliseconds to 0, our assertion will very likely
        // fail later: it would probably have a difference in the 1-999
        // milliseconds range.
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    @Test
    public void testViewConversationMessageLocation() throws GeneralException, NotFoundException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "messages/mesid")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_CONVERSATION_MESSAGE_LOCATION));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        ConversationMessage conversationMessage
                = messageBirdClient.viewConversationMessage("mesid");
        ConversationContentLocation location = conversationMessage.getContent().getLocation();

        assertEquals("mesid", conversationMessage.getId());
        assertEquals(ConversationContentType.LOCATION, conversationMessage.getType());
        assertEquals(52.344263, location.getLatitude(), EPSILON_LOCATION_EQUALITY);
        assertEquals(4.911627, location.getLongitude(), EPSILON_LOCATION_EQUALITY);
    }

    @Test
    public void testViewConversationMessageText() throws GeneralException, NotFoundException, UnauthorizedException {
        MessageBirdService messageBirdService = SpyService
                .expects("GET", "messages/mesid")
                .withConversationsAPIBaseURL()
                .andReturns(new APIResponse(JSON_CONVERSATION_MESSAGE_TEXT));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        ConversationMessage conversationMessage
                = messageBirdClient.viewConversationMessage( "mesid");

        assertEquals("mesid", conversationMessage.getId());
        assertEquals("chanid", conversationMessage.getChannelId());
        assertEquals("convid", conversationMessage.getConversationId());
        assertEquals("Hello", conversationMessage.getContent().getText());
        assertEquals(ConversationContentType.TEXT, conversationMessage.getType());
        assertEquals(ConversationMessageDirection.RECEIVED, conversationMessage.getDirection());
        assertEquals(ConversationMessageStatus.RECEIVED, conversationMessage.getStatus());
    }
}
