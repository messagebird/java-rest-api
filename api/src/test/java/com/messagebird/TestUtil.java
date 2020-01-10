package com.messagebird;

import com.messagebird.objects.*;
import com.messagebird.objects.conversations.*;
import com.messagebird.objects.voicecalls.*;

import java.util.*;

class TestUtil {

    private TestUtil() {
    }

    static VoiceCall createVoiceCall(String destination) {
        final VoiceCall voiceCall = new VoiceCall();
        voiceCall.setSource("ANY_SOURCE");
        voiceCall.setDestination(destination);

        final VoiceCallFlow voiceCallFlow = new VoiceCallFlow();
        voiceCallFlow.setTitle("Test title");
        VoiceStep voiceStep = new VoiceStep();
        voiceStep.setAction("say");

        final VoiceStepOption voiceStepOption = new VoiceStepOption();
        voiceStepOption.setPayload("This is a journey into sound. Good bye!");
        voiceStepOption.setVoice(VoiceType.male.getValue());
        voiceStepOption.setLanguage("en-US");
        voiceStep.setOptions(voiceStepOption);

        voiceCallFlow.setSteps(Collections.singletonList(voiceStep));
        voiceCall.setCallFlow(voiceCallFlow);
        return voiceCall;
    }

    private static VoiceCallData createVoiceCallData() {
        final VoiceCallData voiceCallData = new VoiceCallData();
        voiceCallData.setId("ANY_ID");
        voiceCallData.setDestination("ANY_DESTINATION");
        voiceCallData.setSource("ANY_SOURCE");
        voiceCallData.setStatus(VoiceCallStatus.ended);
        return voiceCallData;
    }

    static VoiceCallResponse createVoiceCallResponse() {
        final VoiceCallResponse voiceCallResponse = new VoiceCallResponse();
        final VoiceCallData voiceCallData = new VoiceCallData();
        voiceCallData.setId("ANY_ID");
        voiceCallData.setStatus(VoiceCallStatus.ended);
        voiceCallData.setDestination("ANY_DESTINATION");
        voiceCallData.setSource("ANY_SOURCE");
        voiceCallResponse.setData(Collections.singletonList(voiceCallData));
        return voiceCallResponse;
    }

    static VoiceCallResponseList createVoiceCallResponseList() {
        final VoiceCallResponseList voiceCallResponseList = new VoiceCallResponseList();
        voiceCallResponseList.setData(Collections.singletonList(createVoiceCallData()));
        return voiceCallResponseList;
    }

    private static Recording createRecording() {
        final Recording recording = new Recording();
        recording.setId("ANY_ID");
        recording.setFormat("wav");
        recording.setType("ivr");
        recording.setLegId("ANY_LEG_ID");
        recording.setState("done");
        recording.setDuration(42);
        recording.setCreatedAt(new Date());
        recording.setUpdatedAt(new Date());

        Map<String, String> links = new LinkedHashMap<>();
        links.put("self", "ANY_SELF");
        links.put("file", "ANY_FILE");
        recording.setLinks(links);
        return recording;
    }

    static RecordingResponse createRecordingResponse(){
        Map<String, String> links = new LinkedHashMap<>();
        links.put("self", "ANY_SELF");
        links.put("file", "ANY_FILE");
        return new RecordingResponse(Collections.singletonList(createRecording()), links, new Pagination());
    }

    static RecordingResponse createRecordingResponseList() {
        Map<String, String> links = new LinkedHashMap<>();
        links.put("self", "ANY_SELF");
        links.put("file", "ANY_FILE");
        List<Recording> recordingList = new ArrayList<>();
        recordingList.add(createRecording());
        recordingList.add(createRecording());
        return new RecordingResponse(recordingList, links, new Pagination());
    }

    static String createDownloadPath(String recordId , String basePath)  {
        return String.format("%s/%s%s",basePath, recordId, MessageBirdClient.RECORDING_DOWNLOAD_FORMAT);
    }

    static TranscriptionResponse createTranscriptionResponse() {
        final TranscriptionResponse transcriptionResponse = new TranscriptionResponse();
        final Transcription transcription = new Transcription();
        transcription.setId("ANY_ID");
        transcription.setRecordingId("ANY_ID");
        transcription.setCreatedAt(new Date());
        transcriptionResponse.setData(Collections.singletonList(transcription));
        return transcriptionResponse;
    }

    static Webhook createWebhook() {
        final Webhook webhook = new Webhook();
        webhook.setUrl("ANY_URL");
        return webhook;
    }

    private static WebhookResponse createWebhookResponse() {
        final WebhookResponse webhookResponse = new WebhookResponse();
        webhookResponse.setId("ANY_ID");
        webhookResponse.setUrl("ANY_URL");
        webhookResponse.setToken("ANY_TOKEN");
        return webhookResponse;
    }

    static WebhookResponseData createWebhookResponseData() {
        final WebhookResponseData webhookResponseData = new WebhookResponseData();
        webhookResponseData.setData(Collections.singletonList(createWebhookResponse()));
        return webhookResponseData;
    }

    static WebhookList createWebhookList() {
        final WebhookList webhookList = new WebhookList();
        webhookList.setData(Collections.singletonList(createWebhookResponse()));
        webhookList.setLinks(Collections.singletonMap("self", "ANY_ID"));
        return webhookList;
    }

    private static Contact createContact(){
        final CustomDetails customDetails = new CustomDetails();
        customDetails.setCustom1("ANY_DETAIL");

        final MessageReference messageReference =  new MessageReference();
        messageReference.setHREF("ANY_HREF");
        messageReference.setTotalCount(30);

        final GroupReference groupReference = new GroupReference();
        groupReference.setHREF("ANY_HREF");
        groupReference.setTotalCount(30);

        final Contact contact = new Contact();
        contact.setFirstName("ANY_NAME");
        contact.setLastName("ANY_LAST_NAME");
        contact.setId("ANY_ID");
        contact.setCreatedDatetime(new Date());
        contact.setMsisdn("ANY_MSISDN");
        contact.setCustomDetails(customDetails);
        contact.setMessages(messageReference);
        contact.setHref("ANY_HREF");
        contact.setGroups(groupReference);
        contact.setUpdatedDatetime(new Date());

        return contact;
    }
    static ContactList createContactList() {
        final ContactList contactList = new ContactList();
        contactList.setItems(Collections.singletonList(createContact()));
        return contactList;
    }

    private static VoiceStepOption createVoiceStepOption()
    {
        final VoiceStepOption voiceStepOption = new VoiceStepOption();
        voiceStepOption.setDestination("123");
        voiceStepOption.setPayload("Test payload Update");
        voiceStepOption.setLanguage("en-US");
        voiceStepOption.setVoice("female");
        voiceStepOption.setRepeat(5);
        voiceStepOption.setMedia("test.wav");
        voiceStepOption.setLength(10);
        voiceStepOption.setMaxLength(20);
        voiceStepOption.setTimeout(30);
        voiceStepOption.setFinishOnKey("#");
        voiceStepOption.setTranscribe(true);
        voiceStepOption.setTranscribeLanguage("en-US");
        voiceStepOption.setRecord("in");
        voiceStepOption.setUrl("http://www.");
        voiceStepOption.setIfMachine("machine1");
        voiceStepOption.setMachineTimeout(2000);
        voiceStepOption.setOnFinish("http://www.");
        voiceStepOption.setMask(false);
        return voiceStepOption;
    }

    public static VoiceStep createVoiceStep() {
        final VoiceStep voiceStep = new VoiceStep();
        voiceStep.setId("a8e44a38-b935-482f-b17f-ed3472c6292c");
        voiceStep.setAction("transfer");
        voiceStep.setOptions(createVoiceStepOption());
        return voiceStep;
    }

    public static VoiceCallFlowRequest createVoiceCallFlowRequest() {
        final VoiceCallFlowRequest voiceCallFlow = new VoiceCallFlowRequest();
        voiceCallFlow.setTitle("ANY_TITLE");
        voiceCallFlow.setRecord(true);
        voiceCallFlow.setSteps(Collections.singletonList(createVoiceStep()));
        voiceCallFlow.setDefaultCall(true);

        return voiceCallFlow;
    }

    static ConversationWebhook createConversationWebhook() {
        ConversationWebhook conversationWebhookResponse = new ConversationWebhook();
        conversationWebhookResponse.setId("whid");
        conversationWebhookResponse.setUrl("https://example.com/webhooks");
        conversationWebhookResponse.setChannelId("chanid");
        conversationWebhookResponse.setEvents(Collections.singletonList(
                ConversationWebhookEvent.MESSAGE_CREATED
        ));
        return conversationWebhookResponse;
    }

    static ConversationWebhookUpdateRequest createConversationWebhookUpdateRequest() {
        return new ConversationWebhookUpdateRequest(
                ConversationWebhookStatus.ENABLED,
                "https://example.com/webhooks",
                Arrays.asList(
                        ConversationWebhookEvent.CONVERSATION_UPDATED,
                        ConversationWebhookEvent.MESSAGE_UPDATED
                )
        );
    }

    static ConversationWebhookCreateRequest createConversationWebhookRequest() {
        return new ConversationWebhookCreateRequest(
                "chanid",
                "https://example.com/webhooks",
                Arrays.asList(
                        ConversationWebhookEvent.CONVERSATION_CREATED,
                        ConversationWebhookEvent.MESSAGE_CREATED
                )
        );
    }
}
