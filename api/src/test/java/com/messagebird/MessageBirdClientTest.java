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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertTrue(balance.getType() != null);
        assertTrue(balance.getPayment() != null);
    }

    @Test
    public void testGetHlr() throws Exception {
        final Hlr hlr = messageBirdClient.getRequestHlr(messageBirdMSISDN, "Test Reference " + messageBirdMSISDN);
        assertTrue(hlr.getReference().equals("Test Reference " + messageBirdMSISDN));
        final String id = hlr.getId();
        assertTrue(id != null);

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
        assertTrue(list.getOffset() != null);
        assertTrue(list.getLinks() != null);
        assertTrue(list.getTotalCount() != null);
        assertTrue(list.getLinks() != null);
    }

    @Test
    public void testListMessagesLimit45() throws Exception {
        final MessageList list = messageBirdClient.listMessages(null, 50);
    }

    @Test
    public void testListMessagesOffset45() throws Exception {
        final MessageList list = messageBirdClient.listMessages(45, null);
        assertTrue(list.getOffset() == 45);
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
    public void testSendDeletePremiumMessage()  throws UnauthorizedException {
        /* Premium is still under development */
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final String reference = "My Reference Über € " + messageBirdMSISDN;
        Message message = new Message("originator", body, messageBirdMSISDN.toString());
        message.setPremiumSMS(1008, "new keyword", 10.23, 2);
        message.setReference(reference);
        final MessageResponse mr;
        try {
            mr = messageBirdClient.sendMessage(message);
        } catch (GeneralException e) {
            final List<ErrorReport> result = e.getErrors();
            // Exception is expected but we continue to test if the result is correct
            boolean hasKeywordError=false;
            boolean hasShortcodeError=false;
            for (final ErrorReport error : e.getErrors()) {
                if ("typeDetails.keyword".equals(error.getParameter())) {
                    hasKeywordError=true;
                }
            }
            assertTrue("Error report doesn't contain error about keyword", hasKeywordError);
        }

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendDeleteMessage1() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendMessage("originator", body, Arrays.asList(new BigInteger[]{messageBirdMSISDN}));
        assertTrue(mr.getId() != null);

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendMessageTestOriginatorLength() throws Exception {
        // test if our local object does truncate correctly
        Message originatorTest = new Message("originator1234567890", "Foo", Arrays.asList(new BigInteger[]{messageBirdMSISDN}));
        assertTrue(originatorTest.getOriginator().length() == 17);

        // test of the server returns us the same
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendMessage("12345678901234567890", body, Arrays.asList(new BigInteger[]{messageBirdMSISDN}));
        // originator get's truncated to 17 chars and when it's numeric it will be prefixed with +, that's ok
        assertTrue(mr.getOriginator().equals("+12345678901234567"));

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendDeleteMessage2() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final String reference = "My Reference Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendMessage("originator", body, Arrays.asList(new BigInteger[]{messageBirdMSISDN}), reference);
        assertTrue(mr.getId() != null);
        assertTrue(mr.getReference().equals(reference));

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendDeleteFlashMessage() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendFlashMessage("originator", body, Arrays.asList(new BigInteger[]{messageBirdMSISDN}));
        assertTrue(mr.getId() != null);
        assertTrue(mr.getType() == MsgType.flash);
        assertTrue(mr.getMclass() == MClassType.flash);

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteMessage(mr.getId());
    }

    @Test
    public void testSendDeleteFlashMessage2() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final String reference = "My Reference Über € " + messageBirdMSISDN;
        final MessageResponse mr = messageBirdClient.sendFlashMessage("originator", body, Arrays.asList(new BigInteger[]{messageBirdMSISDN}), reference);
        assertTrue(mr.getId() != null);
        assertTrue(mr.getReference().equals(reference));

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
        assertTrue(list.getOffset() != null);
        assertTrue(list.getLinks() != null);
        assertTrue(list.getTotalCount() != null);
        // We cannot test actual retrieval of messages because the account may be empty
        assertTrue(list.getLinks() != null);
    }

    @Test
    public void testVoiceListMessagesLimit45() throws Exception {
        final VoiceMessageList list = messageBirdClient.listVoiceMessages(null, 50);
    }

    @Test
    public void testVoiceListMessagesOffset45() throws Exception {
        final VoiceMessageList list = messageBirdClient.listVoiceMessages(45, null);
        assertTrue(list.getOffset() == 45);
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
        assertTrue(mr.getId() != null);
        assertTrue(mr.getBody().equals(body));
        assertTrue(mr.getIfMachine() == IfMachineType.hangup);
        assertTrue(mr.getVoice() == VoiceType.male);

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteVoiceMessage(mr.getId());
    }

    @Test
    public void testSendVoiceMessage1() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final VoiceMessageResponse mr = messageBirdClient.sendVoiceMessage(body, Arrays.asList(new BigInteger[]{messageBirdMSISDN}));
        assertTrue(mr.getId() != null);
        assertTrue(mr.getBody().equals(body));

        // Deleting of a message is not yet supported in test mode
        // Thread.sleep(1000);
        // Gives 404 messageBirdClient.deleteVoiceMessage(mr.getId());
    }

    @Test
    public void testSendVoiceMessage2() throws Exception {
        final String body = "Body test message Über € " + messageBirdMSISDN;
        final String reference = "My Voice Reference Über " + messageBirdMSISDN;
        final VoiceMessageResponse mr = messageBirdClient.sendVoiceMessage(body, Arrays.asList(new BigInteger[]{messageBirdMSISDN}), reference);
        assertTrue(mr.getId() != null);
        assertTrue(mr.getBody().equals(body));
        assertTrue(mr.getReference().equals(reference));

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
        VerifyRequest verifyRequest = new VerifyRequest(this.messageBirdMSISDN.toString());
        verifyRequest.setOriginator("Code");
        verifyRequest.setReference(reference);
        verifyRequest.setLanguage(Language.NL_NL);
        verifyRequest.setType(MsgType.sms);
        verifyRequest.setTimeout(30);
        verifyRequest.setTokenLength(6);
        verifyRequest.setVoice(Gender.FEMALE);
        Verify verify = messageBirdClient.sendVerifyToken(verifyRequest);
        assertFalse("href is empty", verify.getHref().isEmpty());
    }

    @Test
    public void testSendVerifyTokenAndGetVerifyObject() throws UnauthorizedException, GeneralException, NotFoundException {
        Verify verify =  messageBirdClient.sendVerifyToken(this.messageBirdMSISDN.toString());
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
        Verify verify = messageBirdClient.sendVerifyToken(this.messageBirdMSISDN.toString());
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
        Verify verify = messageBirdClient.sendVerifyToken(this.messageBirdMSISDN.toString());
        assertFalse("href is empty", verify.getHref().isEmpty());
        try {
            messageBirdClient.deleteVerifyObject(verify.getId());
        } catch (NotFoundException e) {
            // We expect it to be "Not found" as a test key doesn't create
            // an object in the API.
        }
    }
}
