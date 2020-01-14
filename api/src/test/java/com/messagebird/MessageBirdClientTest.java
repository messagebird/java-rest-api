package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import com.messagebird.objects.voicecalls.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.messagebird.MessageBirdClient.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Created by rvt on 1/8/15.
 */
public class MessageBirdClientTest {

    private static String messageBirdAccessKey = null;
    private static BigInteger messageBirdMSISDN = null;
    MessageBirdServiceImpl messageBirdService;
    MessageBirdClient messageBirdClient;

    @BeforeClass
    public static void setUpClass() {
        messageBirdAccessKey = System.getProperty("messageBirdAccessKey");
        messageBirdMSISDN = new BigInteger(System.getProperty("messageBirdMSISDN"));
    }

    @Before
    public void initialize() {
        messageBirdService = new MessageBirdServiceImpl(messageBirdAccessKey);
        messageBirdClient = new MessageBirdClient(messageBirdService);
    }

    /*********************************************************************/
    /** Other REST services                                                    **/
    /**
     * *****************************************************************
     */
    @Test
    public void testGetBalance() throws Exception {
        final Balance balance = messageBirdClient.getBalance();
        assertNotNull(balance.getType());
        assertNotNull(balance.getPayment());
    }

    @Test
    public void testGetHlr() throws Exception {
        final Hlr hlr = messageBirdClient.getRequestHlr(messageBirdMSISDN, "Test Reference " + messageBirdMSISDN);
        assertEquals(hlr.getReference(), "Test Reference " + messageBirdMSISDN);
        final String id = hlr.getId();
        assertNotNull(id);

        /* During test we cannot re-fetch a HLR
        final Hlr hlr2 = messageBirdClient.getViewHlr(id);
        assertTrue(hlr2.getId().equals(id));
        assertTrue(hlr2.getReference().equals("Test Reference " + messageBirdMSISDN));
        */
    }

    @Test(expected = NotFoundException.class)
    public void testGetViewHlr() throws Exception {
        messageBirdClient.getViewHlr("Foo");
    }


    /*********************************************************************/
    /** Test Listing of messages                                        **/
    /**
     * *****************************************************************
     */
    @Test
    public void testListMessages() throws Exception {
        final MessageList list = messageBirdClient.listMessages(null, null);
        assertNotNull(list.getOffset());
        assertNotNull(list.getLinks());
        assertNotNull(list.getTotalCount());
        assertNotNull(list.getLinks());
    }

    @Test
    public void testListMessagesLimit45() throws Exception {
        final MessageList list = messageBirdClient.listMessages(null, 50);
    }

    @Test
    public void testListMessagesOffset45() throws Exception {
        final MessageList list = messageBirdClient.listMessages(45, null);
        assertEquals(45, (int) list.getOffset());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteMessage() throws Exception {
        messageBirdClient.deleteMessage("Foo");
    }

    /*********************************************************************/
    /** Test message system                                                    **/
    /*********************************************************************/
    @Test
    public void testSendDeleteMessage() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final String reference = "My Reference Über € " + messageBirdMSISDN;
        Message message = new Message("originator", body, messageBirdMSISDN.toString());
        message.setReference(reference);
        final MessageResponse mr = messageBirdClient.sendMessage(message);

        assertNotNull(mr.getId());
        assertEquals(mr.getReference(), reference);
        assertEquals(mr.getBody(), body);
        assertEquals(mr.getDatacoding(), DataCodingType.plain);

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendDeleteMessage1() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendMessage("originator", body, Collections.singletonList(messageBirdMSISDN));
        assertNotNull(mr.getId());

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendMessageWithoutVersion() throws Exception {
        // Some runtimes, like Android, do not set the java.version system property.
        // This test asserts we can still send a message without it.

        String javaVersionBeforeTest = System.getProperty("java.version");
        System.setProperty("java.version", "");

        final String body = "Body test message Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendMessage("originator", body, Collections.singletonList(messageBirdMSISDN));

        assertNotNull(mr.getId());

        // Restore the java.version for other tests.
        System.setProperty("java.version", javaVersionBeforeTest);
    }

    @Test
    public void testSendMessageTestOriginatorLength() throws Exception {
        // test if our local object does truncate correctly
        Message originatorTest = new Message("originator1234567890", "Foo", Collections.singletonList(messageBirdMSISDN));
        assertEquals(17, originatorTest.getOriginator().length());

        // test of the server returns us the same
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendMessage("12345678901234567890", body, Collections.singletonList(messageBirdMSISDN));
        // originator get's truncated to 17 chars and when it's numeric it will be prefixed with +, that's ok
        assertEquals("+12345678901234567", mr.getOriginator());

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendDeleteMessage2() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final String reference = "My Reference Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendMessage("originator", body, Collections.singletonList(messageBirdMSISDN), reference);
        assertNotNull(mr.getId());
        assertEquals(mr.getReference(), reference);

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendDeleteFlashMessage() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendFlashMessage("originator", body, Collections.singletonList(messageBirdMSISDN));
        assertNotNull(mr.getId());
        assertSame(mr.getType(), MsgType.flash);
        assertSame(mr.getMclass(), MClassType.flash);

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendDeleteFlashMessage2() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final String reference = "My Reference Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendFlashMessage("originator", body, Collections.singletonList(messageBirdMSISDN), reference);
        assertNotNull(mr.getId());
        assertEquals(mr.getReference(), reference);

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    /**
     * Note: we test if the method call is successfully by monitoring the NotFoundException
     * which get0s returned only after the server responds
     *
     * @throws Exception
     */
    @Test(expected = NotFoundException.class)
    public void testViewMessage() throws Exception {
        final MessageResponse mr2 = messageBirdClient.viewMessage("Foo");
    }

    /****************************************************************************/
    /** Test Listing of Voice messages                                         **/
    /**
     * ************************************************************************
     */
    @Test
    public void testVoiceListMessages() throws Exception {
        final VoiceMessageList list = messageBirdClient.listVoiceMessages(null, null);
        assertNotNull(list.getOffset());
        assertNotNull(list.getLinks());
        assertNotNull(list.getTotalCount());
        // We cannot test actual retrieval of messages because the account may be empty
        assertNotNull(list.getLinks());
    }

    @Test
    public void testVoiceListMessagesLimit45() throws Exception {
        final VoiceMessageList list = messageBirdClient.listVoiceMessages(null, 50);
    }

    @Test
    public void testVoiceListMessagesOffset45() throws Exception {
        final VoiceMessageList list = messageBirdClient.listVoiceMessages(45, null);
        assertEquals(45, (int) list.getOffset());
    }

    /****************************************************************************/
    /** Test Voice message system                                              **/
    /**
     * ************************************************************************
     */
    @Test
    public void testSendVoiceMessage() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final VoiceMessage vm = new VoiceMessage(body, messageBirdMSISDN.toString());
        vm.setIfMachine(IfMachineType.hangup);
        vm.setVoice(VoiceType.male);
        final VoiceMessageResponse mr = messageBirdClient.sendVoiceMessage(vm);
        assertNotNull(mr.getId());
        assertEquals(mr.getBody(), body);
        assertSame(mr.getIfMachine(), IfMachineType.hangup);
        assertSame(mr.getVoice(), VoiceType.male);

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteVoiceMessage(mr.getId());
    }

    @Test
    public void testSendVoiceMessage1() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final VoiceMessageResponse mr = messageBirdClient.sendVoiceMessage(body, Collections.singletonList(messageBirdMSISDN));
        assertNotNull(mr.getId());
        assertEquals(mr.getBody(), body);

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteVoiceMessage(mr.getId());
    }

    @Test
    public void testSendVoiceMessage2() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final String reference = "My Voice Reference Über " + messageBirdMSISDN;
        final VoiceMessageResponse mr = messageBirdClient.sendVoiceMessage(body, Collections.singletonList(messageBirdMSISDN), reference);
        assertNotNull(mr.getId());
        assertEquals(mr.getBody(), body);
        assertEquals(mr.getReference(), reference);

        Thread.sleep(500);
        // Viewing of a message is not yet supported in test mode
        // final VoiceMessageResponse mr2 = messageBirdClient.viewVoiceMessage(mr.getId());
        // assertTrue(mr2.getId() != null);
        // assertTrue(mr2.getBody().equals(body));
        // assertTrue(mr2.getReference().equals(reference));

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteVoiceMessage(mr.getId());
    }

    /**
     * Note: we test if the method call is successfully by monitoring the NotFoundException
     * which get0s returned only after the server responds
     *
     * @throws Exception
     */
    @Test(expected = NotFoundException.class)
    public void testViewVoiceMessage() throws Exception {
        final VoiceMessageResponse mr2 = messageBirdClient.viewVoiceMessage("Foo");
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteVoiceMessage() throws Exception {
        messageBirdClient.deleteVoiceMessage("Foo");
    }

    @Test
    public void testSendVerifyToken1() throws UnauthorizedException, GeneralException {
        final String reference = "5551234";
        VerifyRequest verifyRequest = new VerifyRequest(messageBirdMSISDN.toString());
        verifyRequest.setOriginator("Code");
        verifyRequest.setReference(reference);
        verifyRequest.setLanguage(Language.NL_NL);
        verifyRequest.setType(VerifyType.SMS);
        verifyRequest.setTimeout(30);
        verifyRequest.setTokenLength(6);
        verifyRequest.setVoice(Gender.FEMALE);
        Verify verify = messageBirdClient.sendVerifyToken(verifyRequest);
        assertFalse("href is empty", verify.getHref().isEmpty());
    }

    @Test
    public void testSendVerifyTokenAndGetVerifyObject() throws UnauthorizedException, GeneralException, NotFoundException {
        Verify verify = messageBirdClient.sendVerifyToken(messageBirdMSISDN.toString());
        assertFalse("href is empty", verify.getHref().isEmpty());
        assertFalse("id is empty", verify.getId().isEmpty());
        try {
            verify = messageBirdClient.getVerifyObject(verify.getId());
        } catch (NotFoundException e) {
            // It is fine if we get not found exception for test as we don't really know the token anyway in test api
        }
        assertFalse("href is empty", verify.getHref().isEmpty());
        assertFalse("id is empty", verify.getId().isEmpty());
    }

    @Test
    public void testVerifyToken() throws UnauthorizedException, GeneralException, UnsupportedEncodingException {
        Verify verify = messageBirdClient.sendVerifyToken(messageBirdMSISDN.toString());
        assertFalse("href is empty", verify.getHref().isEmpty());

        try {
            messageBirdClient.verifyToken(verify.getId(), "123456");
        } catch (NotFoundException e) {
            // It is fine if we get not found exception for test as we don't really know the token anyway in test api
        } catch (GeneralException e) {
            // we expect only one error about token and nothing else
            assertEquals("token", e.getErrors().get(0).getParameter());
            assertEquals(1, e.getErrors().size());
        }
    }

    @Test
    public void testDeleteVerifyToken() throws UnauthorizedException, GeneralException, NotFoundException, UnsupportedEncodingException {
        Verify verify = messageBirdClient.sendVerifyToken(messageBirdMSISDN.toString());
        assertFalse("href is empty", verify.getHref().isEmpty());
        try {
            messageBirdClient.deleteVerifyObject(verify.getId());
        } catch (NotFoundException e) {
            // We expect it to be "Not found" as a test key doesn't create
            // an object in the API.
        }
    }

    @Test
    public void testSendVoiceCalls() throws Exception {

        final VoiceCall voiceCall = TestUtil.createVoiceCall(messageBirdMSISDN.toString());
        final VoiceCallResponse voiceCallResponse = TestUtil.createVoiceCallResponse();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        when(messageBirdServiceMock.sendPayLoad(VOICE_CALLS_BASE_URL + VOICECALLSPATH, voiceCall, VoiceCallResponse.class))
                .thenReturn(voiceCallResponse);

        final VoiceCallResponse response = messageBirdClientInjectMock.sendVoiceCall(voiceCall);
        verify(messageBirdServiceMock, times(1))
                .sendPayLoad(VOICE_CALLS_BASE_URL + VOICECALLSPATH, voiceCall, VoiceCallResponse.class);
        assertNotNull(response);
        assertEquals(response.getData().get(0).getDestination(), voiceCallResponse.getData().get(0).getDestination());
        assertEquals(response.getData().get(0).getSource(), voiceCallResponse.getData().get(0).getSource());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenSourceOfVoiceCallIsMissing() throws UnauthorizedException,
            GeneralException {
        final VoiceCall voiceCall = new VoiceCall();
        voiceCall.setDestination("ANY_DESTINATION");

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
        messageBirdClient.sendVoiceCall(voiceCall);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenDestinationOfVoiceCallIsMissing() throws UnauthorizedException,
            GeneralException {
        final VoiceCall voiceCall = new VoiceCall();
        voiceCall.setSource("ANY_SOURCE");

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
        messageBirdClient.sendVoiceCall(voiceCall);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCallFlowOfVoiceCallIsMissing() throws UnauthorizedException,
            GeneralException {
        final VoiceCall voiceCall = new VoiceCall();
        voiceCall.setSource("ANY_SOURCE");
        voiceCall.setDestination("ANY_DESTINATION");

        messageBirdClient.sendVoiceCall(voiceCall);
    }

    @Test
    public void testViewVoiceCall() throws UnauthorizedException, GeneralException, NotFoundException {
        final VoiceCallResponse voiceCallResponse = TestUtil.createVoiceCallResponse();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        when(messageBirdServiceMock.requestByID(VOICE_CALLS_BASE_URL + VOICECALLSPATH, "ANY_ID",
                VoiceCallResponse.class)).thenReturn(voiceCallResponse);

        final VoiceCallResponse responseFromViewVoiceCall = messageBirdClientInjectMock.viewVoiceCall("ANY_ID");
        verify(messageBirdServiceMock, times(1)).requestByID(VOICE_CALLS_BASE_URL + VOICECALLSPATH, "ANY_ID",
                VoiceCallResponse.class);
        assertNotNull(responseFromViewVoiceCall);
        assertEquals(responseFromViewVoiceCall.getData().get(0).getDestination(), voiceCallResponse.getData().get(0).getDestination());
        assertEquals(responseFromViewVoiceCall.getData().get(0).getSource(), voiceCallResponse.getData().get(0).getSource());
        assertEquals(responseFromViewVoiceCall.getData().get(0).getStatus(), voiceCallResponse.getData().get(0).getStatus());
        assertEquals(responseFromViewVoiceCall.getData().get(0).getId(), voiceCallResponse.getData().get(0).getId());

    }

    @Test
    public void testDeleteVoiceCall() throws UnauthorizedException,
            GeneralException, NotFoundException {

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        doNothing().when(messageBirdServiceMock).deleteByID(VOICE_CALLS_BASE_URL + VOICECALLSPATH, "ANY_ID");
        messageBirdClientInjectMock.deleteVoiceCall("ANY_ID");

    }

    @Test
    public void testListAllVoiceCalls() throws UnauthorizedException, GeneralException {

        final VoiceCallResponseList voiceCallResponseList = TestUtil.createVoiceCallResponseList();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        when(messageBirdServiceMock.requestList(Mockito.eq(VOICE_CALLS_BASE_URL + VOICECALLSPATH), Mockito.isA(PagedPaging.class),
                Mockito.eq(VoiceCallResponseList.class)))
                .thenReturn(voiceCallResponseList);

        final VoiceCallResponseList response = messageBirdClientInjectMock.listAllVoiceCalls(1, 2);
        verify(messageBirdServiceMock, times(1))
                .requestList(Mockito.eq(VOICE_CALLS_BASE_URL + VOICECALLSPATH), Mockito.isA(PagedPaging.class),
                        Mockito.eq(VoiceCallResponseList.class));
        assertEquals(response.getData().get(0).getDestination(), voiceCallResponseList.getData().get(0).getDestination());
        assertEquals(response.getData().get(0).getSource(), voiceCallResponseList.getData().get(0).getSource());
        assertEquals(response.getData().get(0).getStatus(), voiceCallResponseList.getData().get(0).getStatus());
        assertEquals(response.getData().get(0).getId(), voiceCallResponseList.getData().get(0).getId());

    }

    @Test
    public void testViewRecording() throws UnauthorizedException, GeneralException, NotFoundException {
        final RecordingResponse recordingResponse = TestUtil.createRecordingResponse();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        Map<String, Object> params = new LinkedHashMap<>();
        params.put("legs", "ANY_LEG_ID");
        params.put("recordings", "ANY_ID");

        when(messageBirdServiceMock.requestByID(VOICE_CALLS_BASE_URL + VOICECALLSPATH, "ANY_CALL_ID",
                params, RecordingResponse.class)).thenReturn(recordingResponse);

        final RecordingResponse response = messageBirdClientInjectMock.viewRecording("ANY_CALL_ID", "ANY_LEG_ID", "ANY_ID");
        verify(messageBirdServiceMock, times(1)).requestByID(VOICE_CALLS_BASE_URL + VOICECALLSPATH, "ANY_CALL_ID",
                params, RecordingResponse.class);
        assertNotNull(response);
        assertEquals(response.getData().get(0).getId(), recordingResponse.getData().get(0).getId());
        assertEquals(response.getData().get(0).getFormat(), recordingResponse.getData().get(0).getFormat());
        assertEquals(response.getData().get(0).getState(), recordingResponse.getData().get(0).getState());
        assertEquals(response.getData().get(0).getLegId(), recordingResponse.getData().get(0).getLegId());
        assertEquals(response.getData().get(0).getDuration(), recordingResponse.getData().get(0).getDuration());
        assertEquals(response.getData().get(0).getCreatedAt(), recordingResponse.getData().get(0).getCreatedAt());
        assertEquals(response.getData().get(0).getUpdatedAt(), recordingResponse.getData().get(0).getUpdatedAt());
        assertEquals(response.getData().get(0).getLinks().get("self"), recordingResponse.getLinks().get("self"));
        assertEquals(response.getData().get(0).getLinks().get("file"), recordingResponse.getLinks().get("file"));
    }

    @Test
    public void testCreateTranscription() throws UnauthorizedException, GeneralException {

        final TranscriptionResponse transcriptionResponse = TestUtil.createTranscriptionResponse();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        String url = String.format(
                "%s%s/%s%s/%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                "ANY_CALL_ID",
                LEGSPATH,
                "ANY_LEG_ID",
                RECORDINGPATH,
                "ANY_ID",
                TRANSCRIPTIONPATH);

        when(messageBirdServiceMock.sendPayLoad(url, "nl-NL", TranscriptionResponse.class))
                .thenReturn(transcriptionResponse);

        final TranscriptionResponse response = messageBirdClientInjectMock
                .createTranscription("ANY_CALL_ID", "ANY_LEG_ID", "ANY_ID", "nl-NL");

        verify(messageBirdServiceMock, times(1)).sendPayLoad(url, "nl-NL", TranscriptionResponse.class);
        assertNotNull(response);
        assertEquals(response.getData().get(0).getId(), transcriptionResponse.getData().get(0).getId());
        assertEquals(response.getData().get(0).getRecordingId(), transcriptionResponse.getData().get(0).getRecordingId());
        assertEquals(response.getData().get(0).getCreatedAt(), transcriptionResponse.getData().get(0).getCreatedAt());
    }

    @Test
    public void testListRecordings() throws UnauthorizedException, GeneralException {
        final RecordingResponse recordings = TestUtil.createRecordingResponseList();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        String url = String.format(
                "%s%s/%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                "ANY_CALL_ID",
                LEGSPATH,
                "ANY_LEG_ID",
                RECORDINGPATH
        );
        when(messageBirdServiceMock.requestList(url, 0, 0, RecordingResponse.class))
                .thenReturn(recordings);

        final RecordingResponse response = messageBirdClientInjectMock
                .listRecordings("ANY_CALL_ID", "ANY_LEG_ID", 0, 0);
        verify(messageBirdServiceMock, times(1)).requestList(url, 0, 0, RecordingResponse.class);
        assertNotNull(response);
        for(int i = 0; i < response.getData().size() ; i++) {
            assertReflectionEquals(response.getData().get(i), recordings.getData().get(i));
        }
    }

    @Test
    public void testDownloadRecording() throws NotFoundException, GeneralException, UnauthorizedException {
        String recordId = "123123123";
        String basePath = "test";
        String fileName = String.format("%s%s", recordId, RECORDING_DOWNLOAD_FORMAT);
        final String downloadPath = TestUtil.createDownloadPath(recordId, basePath);
        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        String url = String.format(
                "%s%s/%s%s/%s%s/%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                "ANY_CALL_ID",
                LEGSPATH,
                "ANY_LEG_ID",
                RECORDINGPATH,
                fileName
        );
        when(messageBirdServiceMock.getBinaryData(url, basePath, fileName))
                .thenReturn(downloadPath);
        final String response = messageBirdClientInjectMock
                .downloadRecording("ANY_CALL_ID", "ANY_LEG_ID", recordId, basePath);
        verify(messageBirdServiceMock, times(1)).getBinaryData(url, basePath, fileName);
        assertNotNull(response);
        assertEquals(downloadPath, response);
    }

    @Test
    public void testDownloadTranscription() throws NotFoundException, GeneralException, UnauthorizedException {
        String transcriptionId = "123123123";
        String basePath = "test";
        String fileName = String.format("%s%s", transcriptionId, TRANSCRIPTION_DOWNLOAD_FORMAT);
        final String downloadPath = TestUtil.createDownloadPath(transcriptionId, basePath);
        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        String url = String.format(
                "%s%s/%s%s/%s%s/%s%s/%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                "ANY_CALL_ID",
                LEGSPATH,
                "ANY_LEG_ID",
                RECORDINGPATH,
                "ANY_RECORDING_ID",
                TRANSCRIPTIONPATH,
                fileName
        );
        when(messageBirdServiceMock.getBinaryData(url, basePath, fileName))
                .thenReturn(downloadPath);
        final String response = messageBirdClientInjectMock
                .downloadTranscription("ANY_CALL_ID", "ANY_LEG_ID", "ANY_RECORDING_ID", transcriptionId, basePath);
        verify(messageBirdServiceMock, times(1)).getBinaryData(url, basePath, fileName);
        assertNotNull(response);
        assertEquals(downloadPath, response);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenLanguageIsNotSupported() throws UnauthorizedException, GeneralException {
        messageBirdClient.createTranscription("ANY_CALL_ID", "ANY_LEG_ID", "ANY_ID", "tr-TR");
    }

    @Test
    public void testViewTranscription() throws UnauthorizedException, GeneralException, NotFoundException {
        final TranscriptionResponse transcriptionResponse = TestUtil.createTranscriptionResponse();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        String url = String.format(
                "%s%s/%s%s/%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                "ANY_CALL_ID",
                LEGSPATH,
                "ANY_LEG_ID",
                RECORDINGPATH,
                "ANY_ID",
                TRANSCRIPTIONPATH);

        when(messageBirdServiceMock.requestByID(Mockito.eq(url), Mockito.eq("ANY_TRANSCRIPTION_ID"), Mockito.eq(TranscriptionResponse.class)))
                .thenReturn(transcriptionResponse);

        final TranscriptionResponse response = messageBirdClientInjectMock
                .viewTranscription("ANY_CALL_ID", "ANY_LEG_ID", "ANY_ID", "ANY_TRANSCRIPTION_ID");
        verify(messageBirdServiceMock, times(1))
                .requestByID(Mockito.eq(url), Mockito.eq("ANY_TRANSCRIPTION_ID"), Mockito.eq(TranscriptionResponse.class));
        assertNotNull(response);
        assertReflectionEquals(response.getData().get(0), transcriptionResponse.getData().get(0));
    }

    @Test
    public void testCreateWebhook() throws UnauthorizedException, GeneralException {
        final Webhook webhook = TestUtil.createWebhook();
        final WebhookResponseData webhookResponseData = TestUtil.createWebhookResponseData();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        when(messageBirdServiceMock.sendPayLoad(VOICE_CALLS_BASE_URL + WEBHOOKS, webhook, WebhookResponseData.class))
                .thenReturn(webhookResponseData);
        final WebhookResponseData response = messageBirdClientInjectMock.createWebhook(webhook);
        verify(messageBirdServiceMock, times(1)).sendPayLoad(VOICE_CALLS_BASE_URL + WEBHOOKS, webhook, WebhookResponseData.class);
        assertNotNull(response);
        assertEquals(response.getData().get(0).getId(), webhookResponseData.getData().get(0).getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreateWebhookWithMissingUrl() throws UnauthorizedException, GeneralException {
        final Webhook webhook = new Webhook();
        messageBirdClient.createWebhook(webhook);
    }

    @Test
    public void testViewWebhook() throws UnauthorizedException, GeneralException, NotFoundException {
        final WebhookResponseData webhookResponseData = TestUtil.createWebhookResponseData();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        when(messageBirdServiceMock.requestByID(VOICE_CALLS_BASE_URL + WEBHOOKS, "ANY_ID", WebhookResponseData.class))
                .thenReturn(webhookResponseData);
        final WebhookResponseData response = messageBirdClientInjectMock.viewWebhook("ANY_ID");
        verify(messageBirdServiceMock, times(1)).requestByID(VOICE_CALLS_BASE_URL + WEBHOOKS,
                "ANY_ID", WebhookResponseData.class);
        assertNotNull(response);
        assertEquals(response.getData().get(0).getId(), webhookResponseData.getData().get(0).getId());
        assertEquals(response.getData().get(0).getUrl(), webhookResponseData.getData().get(0).getUrl());
    }

    @Test
    public void testListWebhooks() throws UnauthorizedException, GeneralException {
        final WebhookList webhookList = TestUtil.createWebhookList();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientMock = new MessageBirdClient(messageBirdServiceMock);

        when(messageBirdServiceMock.requestList(anyString(), anyInt(), anyInt(), eq(WebhookList.class)))
                .thenReturn(webhookList);
        final WebhookList response = messageBirdClientMock.listWebhooks(0, 0);
        verify(messageBirdServiceMock, times(1))
                .requestList(VOICE_CALLS_BASE_URL + WEBHOOKS, 0, 0, WebhookList.class);
        assertNotNull(response);
        assertEquals(response.getData().get(0).getId(), webhookList.getData().get(0).getId());
        assertEquals(response.getData().get(0).getUrl(), webhookList.getData().get(0).getUrl());
    }

    @Test
    public void testUpdateWebhook() throws UnauthorizedException, GeneralException {
        final Webhook webhook = TestUtil.createWebhook();
        final WebhookResponseData webhookResponseData = TestUtil.createWebhookResponseData();
        final String id = webhookResponseData.getData().get(0).getId();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientMock = new MessageBirdClient(messageBirdServiceMock);

        String url = String.format("%s%s/%s", VOICE_CALLS_BASE_URL, WEBHOOKS, id);
        when(messageBirdServiceMock.sendPayLoad(anyString(), anyString(), eq(webhook), eq(WebhookResponseData.class)))
                .thenReturn(webhookResponseData);
        final WebhookResponseData response = messageBirdClientMock.updateWebhook(id, webhook);
        verify(messageBirdServiceMock, times(1))
                .sendPayLoad("PUT", url, webhook, WebhookResponseData.class);
        assertNotNull(response);
        assertEquals(response.getData().get(0).getUrl(), webhookResponseData.getData().get(0).getUrl());
    }

    @Test
    public void testDeleteWebhook() throws NotFoundException, GeneralException, UnauthorizedException {
        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientMock = new MessageBirdClient(messageBirdServiceMock);

        messageBirdClientMock.deleteWebhook("id");
        verify(messageBirdServiceMock, times(1)).deleteByID(VOICE_CALLS_BASE_URL + WEBHOOKS, "id");
    }

    @Test
    public void testListNumbersForPurchase() throws IllegalArgumentException, GeneralException, UnauthorizedException, NotFoundException {
        final String url = String.format("%s/available-phone-numbers", NUMBERS_CALLS_BASE_URL);
        final PhoneNumbersResponse mockedResponse = new PhoneNumbersResponse();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientMock = new MessageBirdClient(messageBirdServiceMock);

        when(messageBirdServiceMock.requestByID(url, "NL", PhoneNumbersResponse.class))
            .thenReturn(mockedResponse);

        final PhoneNumbersResponse response = messageBirdClientMock.listNumbersForPurchase("NL");
        verify(messageBirdServiceMock, times(1)).requestByID(url, "NL", PhoneNumbersResponse.class);
        
        assertNotNull(response);
        assertEquals(response, mockedResponse);
    }

    @Test
    public void testListNumbersForPurchaseWithParams() throws IllegalArgumentException, GeneralException, UnauthorizedException, NotFoundException {
        final String url = String.format("%s/available-phone-numbers", NUMBERS_CALLS_BASE_URL);
        final PhoneNumbersResponse mockedResponse = new PhoneNumbersResponse();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientMock = new MessageBirdClient(messageBirdServiceMock);

        PhoneNumbersLookup options = new PhoneNumbersLookup();
        options.setFeatures(PhoneNumberFeature.VOICE, PhoneNumberFeature.SMS);
        options.setType(PhoneNumberType.MOBILE);
        options.setLimit(1);
        options.setNumber(562);
        options.setSearchPattern(PhoneNumberSearchPattern.START);
    
        when(messageBirdServiceMock.requestByID(url, "US", options.toHashMap(), PhoneNumbersResponse.class))
            .thenReturn(mockedResponse);

        final PhoneNumbersResponse response = messageBirdClientMock.listNumbersForPurchase("US", options);
        verify(messageBirdServiceMock, times(1)).requestByID(url, "US", options.toHashMap(), PhoneNumbersResponse.class);
        assertNotNull(response);
        assertEquals(response, mockedResponse);
    }

    @Test
    public void testPurchaseNumber() throws UnauthorizedException, GeneralException {
        final String url = String.format("%s/phone-numbers", NUMBERS_CALLS_BASE_URL);

        PurchasedNumberCreatedResponse purchasedNumberMockData = new PurchasedNumberCreatedResponse();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientMock = new MessageBirdClient(messageBirdServiceMock);
        
        final Map<String, Object> payload = new LinkedHashMap<String, Object>();
        payload.put("number", "15625267429");
        payload.put("countryCode", "US");
        payload.put("billingIntervalMonths", 1);
        
        when(messageBirdServiceMock.sendPayLoad(url, payload, PurchasedNumberCreatedResponse.class))
            .thenReturn(purchasedNumberMockData);
        final PurchasedNumberCreatedResponse response = messageBirdClientMock.purchaseNumber("15625267429", "US", 1);
        verify(messageBirdServiceMock, times(1)).sendPayLoad(url, payload, PurchasedNumberCreatedResponse.class);
        assertNotNull(response);
        assertEquals(response, purchasedNumberMockData);
    }
    
    @Test
    public void testListPurchasedNumbers() throws UnauthorizedException, GeneralException, NotFoundException {
        final String url = String.format("%s/phone-numbers", NUMBERS_CALLS_BASE_URL);
    
        PurchasedNumbersResponse purchasedNumbersMockData = new PurchasedNumbersResponse();
    
        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientMock = new MessageBirdClient(messageBirdServiceMock);

        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();
        filter.setLimit(1);
        filter.addFeature(PhoneNumberFeature.SMS);
        filter.setType(PhoneNumberType.MOBILE);
        filter.addTag("tag");

        when(messageBirdServiceMock.requestByID(url, null, filter.toHashMap(), PurchasedNumbersResponse.class))
            .thenReturn(purchasedNumbersMockData);

        final PurchasedNumbersResponse response = messageBirdClientMock.listPurchasedNumbers(filter);

        verify(messageBirdServiceMock, times(1)).requestByID(url, null, filter.toHashMap(), PurchasedNumbersResponse.class);
        assertNotNull(response);
        assertEquals(response, purchasedNumbersMockData);
    }

    @Test
    public void testViewPurchasedNumber()  throws UnauthorizedException, GeneralException, NotFoundException {
        final String url = String.format("%s/phone-numbers", NUMBERS_CALLS_BASE_URL);
    
        PurchasedNumber purchasedNumberMockData = new PurchasedNumber();
    
        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientMock = new MessageBirdClient(messageBirdServiceMock);
        when(messageBirdServiceMock.requestByID(url, "15625267429", PurchasedNumber.class))
            .thenReturn(purchasedNumberMockData);
        final PurchasedNumber response = messageBirdClientMock.viewPurchasedNumber("15625267429");

        verify(messageBirdServiceMock, times(1)).requestByID(url, "15625267429", PurchasedNumber.class);
        assertNotNull(response);
        assertEquals(response, purchasedNumberMockData);
    }

    @Test
    public void updatePurchasedNumber()  throws UnauthorizedException, GeneralException {
        final String phoneNumber = "15625267429";
        final String url = String.format("%s/phone-numbers/%s", NUMBERS_CALLS_BASE_URL, phoneNumber);
    
        PurchasedNumber updatedNumberMock = new PurchasedNumber();
    
        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientMock = new MessageBirdClient(messageBirdServiceMock);
        
        final Map<String, List<String>> payload = new HashMap<String, List<String>>();
        payload.put("tags", Collections.singletonList("tag"));
        
        when(messageBirdServiceMock.sendPayLoad("PATCH", url, payload, PurchasedNumber.class))
            .thenReturn(updatedNumberMock);
        final PurchasedNumber response = messageBirdClientMock.updateNumber(phoneNumber, "tag");
        verify(messageBirdServiceMock, times(1)).sendPayLoad("PATCH", url, payload, PurchasedNumber.class);
        assertNotNull(response);
        assertEquals(response, updatedNumberMock);
    }

    @Test
    public void deletePurchasedNumber()  throws UnauthorizedException, GeneralException, NotFoundException {
        final String phoneNumber = "15625267429";
        final String url = String.format("%s/phone-numbers", NUMBERS_CALLS_BASE_URL);
    
        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientMock = new MessageBirdClient(messageBirdServiceMock);
        
        messageBirdClientMock.cancelNumber(phoneNumber);
        verify(messageBirdServiceMock, times(1)).deleteByID(url, phoneNumber);
    }

}
