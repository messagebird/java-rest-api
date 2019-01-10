package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import static com.messagebird.MessageBirdClient.VOICECALLSPATH;
import static com.messagebird.MessageBirdClient.VOICE_CALLS_BASE_URL;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

        assertTrue(mr.getId() != null);
        assertTrue(mr.getReference().equals(reference));
        assertTrue(mr.getBody().equals(body));
        assertTrue(mr.getDatacoding().equals(DataCodingType.plain));

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendDeleteMessage1() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendMessage("originator", body, Arrays.asList(messageBirdMSISDN));
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
        final MessageResponse mr = messageBirdClient.sendMessage("originator", body, Arrays.asList(messageBirdMSISDN));

        assertNotNull(mr.getId());

        // Restore the java.version for other tests.
        System.setProperty("java.version", javaVersionBeforeTest);
    }

    @Test
    public void testSendMessageTestOriginatorLength() throws Exception {
        // test if our local object does truncate correctly
        Message originatorTest = new Message("originator1234567890", "Foo", Arrays.asList(messageBirdMSISDN));
        assertEquals(17, originatorTest.getOriginator().length());

        // test of the server returns us the same
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendMessage("12345678901234567890", body, Arrays.asList(messageBirdMSISDN));
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
        final MessageResponse mr = messageBirdClient.sendMessage("originator", body, Arrays.asList(messageBirdMSISDN), reference);
        assertNotNull(mr.getId());
        assertEquals(mr.getReference(), reference);

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendDeleteFlashMessage() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendFlashMessage("originator", body, Arrays.asList(messageBirdMSISDN));
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
        final MessageResponse mr = messageBirdClient.sendFlashMessage("originator", body, Arrays.asList(messageBirdMSISDN), reference);
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
        final VoiceMessageResponse mr = messageBirdClient.sendVoiceMessage(body, Arrays.asList(messageBirdMSISDN));
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
        final VoiceMessageResponse mr = messageBirdClient.sendVoiceMessage(body, Arrays.asList(messageBirdMSISDN), reference);
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
            assertTrue(e.getErrors().size() == 1);
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

    /**
     * Test Voice Calling system
     */
    @Test
    public void testSendVoiceCalls() throws Exception {

        final VoiceCall voiceCall = TestUtil.createVoiceCall(messageBirdMSISDN.toString());

        final VoiceCallResponse voiceCallResponse = messageBirdClient.sendVoiceCall(voiceCall);
        assertNotNull(voiceCallResponse);
        assertEquals(voiceCallResponse.getData().get(0).getDestination(), "31633612867");
        assertEquals(voiceCallResponse.getData().get(0).getSource(), "31644556677");

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenSourceOfVoiceCallIsMissing() throws UnauthorizedException,
            GeneralException {
        final VoiceCall voiceCall = new VoiceCall();
        voiceCall.setDestination("31633612867");

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
        voiceCall.setSource("31644556677");

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
        voiceCall.setSource("31644556677");
        voiceCall.setDestination("31633612867");

        messageBirdClient.sendVoiceCall(voiceCall);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenViewVoiceCall() throws Exception {
        messageBirdClient.viewVoiceCall("Foo");
    }

    @Test
    public void testViewVoiceCall() throws UnauthorizedException, GeneralException, NotFoundException {
        final VoiceCallResponse voiceCallResponse = TestUtil.createVoiceCallResponse();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        when(messageBirdServiceMock.requestByID(VOICE_CALLS_BASE_URL + VOICECALLSPATH, "ANY_ID",
                VoiceCallResponse.class)).thenReturn(voiceCallResponse);

        final VoiceCallResponse responseFromViewVoiceCall = messageBirdClientInjectMock.viewVoiceCall("ANY_ID");

        assertNotNull(responseFromViewVoiceCall);
        assertEquals(responseFromViewVoiceCall.getData().get(0).getDestination(), "31633612867");
        assertEquals(responseFromViewVoiceCall.getData().get(0).getSource(), "31644556677");
        assertEquals(responseFromViewVoiceCall.getData().get(0).getStatus(), VoiceCallStatus.ended);
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

        when(messageBirdServiceMock.requestList(VOICE_CALLS_BASE_URL + VOICECALLSPATH, 0, 20, VoiceCallResponseList.class))
                .thenReturn(voiceCallResponseList);

        VoiceCallResponseList response = messageBirdClientInjectMock.listAllVoiceCalls();
        assertEquals(response.getItems().get(0).getData().get(0).getDestination(), voiceCallResponseList.getItems().get(0).getData().get(0).getDestination());
        assertEquals(response.getItems().get(0).getData().get(0).getSource(), voiceCallResponseList.getItems().get(0).getData().get(0).getSource());
        assertEquals(response.getItems().get(0).getData().get(0).getStatus(), voiceCallResponseList.getItems().get(0).getData().get(0).getStatus());
        assertEquals(response.getItems().get(0).getData().get(0).getId(), voiceCallResponseList.getItems().get(0).getData().get(0).getId());

    }
}
