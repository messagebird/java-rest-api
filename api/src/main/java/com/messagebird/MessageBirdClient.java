package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Message bird general client
 * <p>
 * <pre>
 * Initialise this class with a MessageBirdService
 *  // First create your service object
 *  MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);
 *  // Add the service to the client
 *  MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
 *
 *  Then read your balance like this:
 *  final Balance balance = messageBirdClient.getBalance();
 * </pre>
 * <p>
 * Created by rvt on 1/5/15.
 */
public class MessageBirdClient {
    private static final String BALANCEPATH = "/balance";
    private static final String HLRPATH = "/hlr";
    private static final String MESSAGESPATH = "/messages";
    private static final String VOICEMESSAGESPATH = "/voicemessages";
    private static final String VERIFYPATH = "/verify";
    private static final String LOOKUPPATH = "/lookup";
    private static final String LOOKUPHLRPATH = "/lookup/%s/hlr";
    private MessageBirdService messageBirdService;

    public MessageBirdClient(final MessageBirdService messageBirdService) {
        this.messageBirdService = messageBirdService;
    }


    /****************************************************************************************************/
    /** Balance and HRL methods                                                                        **/
    /****************************************************************************************************/

    /**
     * MessageBird provides an API to get the balance information of your account.
     *
     * @return Balance object
     * @throws GeneralException
     * @throws com.messagebird.exceptions.UnauthorizedException
     */
    public Balance getBalance() throws GeneralException, UnauthorizedException, NotFoundException {
        return messageBirdService.requestByID(BALANCEPATH, "", Balance.class);
    }

    /**
     * MessageBird provides an API to send Network Queries to any mobile number across the world.
     * An HLR allows you to view which mobile number (MSISDN) belongs to what operator in real time and see whether the number is active.
     *
     * @param msisdn    The telephone number.
     * @param reference A client reference
     * @return Hlr Object
     * @throws GeneralException
     * @throws UnauthorizedException
     */
    public Hlr getRequestHlr(final BigInteger msisdn, final String reference) throws GeneralException, UnauthorizedException {
        if (msisdn == null) {
            throw new IllegalArgumentException("msisdn must be specified.");
        }
        if (reference == null) {
            throw new IllegalArgumentException("Reference must be specified.");
        }
        final Map<String, Object> payload = new LinkedHashMap<String, Object>();
        payload.put("msisdn", msisdn);
        payload.put("reference", reference);
        return messageBirdService.sendPayLoad(HLRPATH, payload, Hlr.class);
    }

    /**
     * Retrieves the information of an existing HLR. You only need to supply the unique message id that was returned upon creation or receiving.
     *
     * @param hlrId ID as returned by getRequestHlr in the id variable
     * @return Hlr Object
     * @throws GeneralException
     * @throws UnauthorizedException
     */
    public Hlr getViewHlr(final String hlrId) throws GeneralException, UnauthorizedException, NotFoundException {
        if (hlrId == null) {
            throw new IllegalArgumentException("Hrl ID must be specified.");
        }
        return messageBirdService.requestByID(HLRPATH, hlrId, Hlr.class);
    }

    /****************************************************************************************************/
    /** Messaging                                                                                      **/
    /****************************************************************************************************/

    /**
     * Send a message through the messagebird platform
     *
     * @param message Message object to be send
     * @return Message Response
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public MessageResponse sendMessage(final Message message) throws UnauthorizedException, GeneralException {
        return messageBirdService.sendPayLoad(MESSAGESPATH, message, MessageResponse.class);
    }

    /**
     * Convenient function to send a simple message to a list of recipients
     *
     * @param originator Originator of the message, this will get truncated to 11 chars
     * @param body       Body of the message
     * @param recipients List of recipients
     * @return MessageResponse
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public MessageResponse sendMessage(final String originator, final String body, final List<BigInteger> recipients) throws UnauthorizedException, GeneralException {
        return messageBirdService.sendPayLoad(MESSAGESPATH, new Message(originator, body, recipients), MessageResponse.class);
    }

    /**
     * Convenient function to send a simple message to a list of recipients
     *
     * @param originator Originator of the message, this will get truncated to 11 chars
     * @param body       Body of the message
     * @param recipients List of recipients
     * @param reference  your reference
     * @return
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public MessageResponse sendMessage(final String originator, final String body, final List<BigInteger> recipients, final String reference) throws UnauthorizedException, GeneralException {
        final Message message = new Message(originator, body, recipients);
        message.setReference(reference);
        return messageBirdService.sendPayLoad(MESSAGESPATH, message, MessageResponse.class);
    }

    /**
     * Convenient function to send a simple flash message to a list of recipients
     *
     * @param originator Originator of the message, this will get truncated to 11 chars
     * @param body       Body of the message
     * @param recipients List of recipients
     * @return
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public MessageResponse sendFlashMessage(final String originator, final String body, final List<BigInteger> recipients) throws UnauthorizedException, GeneralException {
        final Message message = new Message(originator, body, recipients);
        message.setType(MsgType.flash);
        return messageBirdService.sendPayLoad(MESSAGESPATH, message, MessageResponse.class);
    }

    /**
     * Convenient function to send a simple flash message to a list of recipients
     *
     * @param originator Originator of the message, this will get truncated to 11 chars
     * @param body       Body of the message
     * @param recipients List of recipients
     * @param reference  your reference
     * @return
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public MessageResponse sendFlashMessage(final String originator, final String body, final List<BigInteger> recipients, final String reference) throws UnauthorizedException, GeneralException {
        final Message message = new Message(originator, body, recipients);
        message.setType(MsgType.flash);
        message.setReference(reference);
        return messageBirdService.sendPayLoad(MESSAGESPATH, message, MessageResponse.class);
    }

    public MessageList listMessages(final Integer offset, final Integer limit) throws UnauthorizedException, GeneralException {
        if (offset != null && offset < 0) {
            throw new IllegalArgumentException("Offset must be > 0");
        }
        if (limit != null && limit < 0) {
            throw new IllegalArgumentException("Limit must be > 0");
        }
        return messageBirdService.requestList(MESSAGESPATH, offset, limit, MessageList.class);
    }

    /**
     * Delete a message from the Messagebird server
     *
     * @param id
     * @return void
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public void deleteMessage(final String id) throws UnauthorizedException, GeneralException, NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Message ID must be specified.");
        }
        messageBirdService.deleteByID(MESSAGESPATH, id);
    }

    /**
     * View a message from the Messagebird server
     *
     * @param id
     * @return void
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public MessageResponse viewMessage(final String id) throws UnauthorizedException, GeneralException, NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Message ID must be specified.");
        }
        return messageBirdService.requestByID(MESSAGESPATH, id, MessageResponse.class);
    }

    /****************************************************************************************************/
    /** Voice Messaging                                                                                **/
    /****************************************************************************************************/

    /**
     * Convenient function to send a simple message to a list of recipients
     *
     * @param voiceMessage Voice message object
     * @return
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public VoiceMessageResponse sendVoiceMessage(final VoiceMessage voiceMessage) throws UnauthorizedException, GeneralException {
        return messageBirdService.sendPayLoad(VOICEMESSAGESPATH, voiceMessage, VoiceMessageResponse.class);
    }

    /**
     * Convenient function to send a simple message to a list of recipients
     *
     * @param body       Body of the message
     * @param recipients List of recipients
     * @return
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public VoiceMessageResponse sendVoiceMessage(final String body, final List<BigInteger> recipients) throws UnauthorizedException, GeneralException {
        final VoiceMessage message = new VoiceMessage(body, recipients);
        return messageBirdService.sendPayLoad(VOICEMESSAGESPATH, message, VoiceMessageResponse.class);
    }

    /**
     * Convenient function to send a simple message to a list of recipients
     *
     * @param body       Body of the message
     * @param recipients List of recipients
     * @param reference  your reference
     * @return
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public VoiceMessageResponse sendVoiceMessage(final String body, final List<BigInteger> recipients, final String reference) throws UnauthorizedException, GeneralException {
        final VoiceMessage message = new VoiceMessage(body, recipients);
        message.setReference(reference);
        return messageBirdService.sendPayLoad(VOICEMESSAGESPATH, message, VoiceMessageResponse.class);
    }

    /**
     * Delete a voice message from the Messagebird server
     *
     * @param id
     * @return void
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public void deleteVoiceMessage(final String id) throws UnauthorizedException, GeneralException, NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Message ID must be specified.");
        }
        messageBirdService.deleteByID(VOICEMESSAGESPATH, id);
    }

    /**
     * View a message from the Messagebird server
     *
     * @param id
     * @return void
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public VoiceMessageResponse viewVoiceMessage(final String id) throws UnauthorizedException, GeneralException, NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Voice Message ID must be specified.");
        }
        return messageBirdService.requestByID(VOICEMESSAGESPATH, id, VoiceMessageResponse.class);
    }

    /**
     * List voice messages
     *
     * @param offset
     * @param limit
     * @return
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public VoiceMessageList listVoiceMessages(final Integer offset, final Integer limit) throws UnauthorizedException, GeneralException {
        if (offset != null && offset < 0) {
            throw new IllegalArgumentException("Offset must be > 0");
        }
        if (limit != null && limit < 0) {
            throw new IllegalArgumentException("Limit must be > 0");
        }
        return messageBirdService.requestList(VOICEMESSAGESPATH, offset, limit, VoiceMessageList.class);
    }

    /**
     * @param verifyRequest
     * @return Verify object
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public Verify sendVerifyToken(VerifyRequest verifyRequest) throws UnauthorizedException, GeneralException {
        if (verifyRequest == null) {
            throw new IllegalArgumentException("Verify request cannot be null");
        } else if (verifyRequest.getRecipient() == null || verifyRequest.getRecipient().isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be empty for verify");
        }
        return messageBirdService.sendPayLoad(VERIFYPATH, verifyRequest, Verify.class);
    }

    /**
     * @param recipient
     * @return Verify object
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public Verify sendVerifyToken(String recipient) throws UnauthorizedException, GeneralException {
        if (recipient == null || recipient.isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be empty for verify");
        }
        VerifyRequest verifyRequest = new VerifyRequest(recipient);
        return this.sendVerifyToken(verifyRequest);
    }

    /**
     *
     * @param id
     * @param token
     * @return Verify object
     * @throws NotFoundException
     * @throws GeneralException
     * @throws UnauthorizedException
     */
    public Verify verifyToken(String id, String token) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty for verify");
        } else if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty for verify");
        }
        final Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("token", token);
        return messageBirdService.requestByID(VERIFYPATH, id, params, Verify.class);
    }

    /**
     *
     * @param id
     * @return Verify object
     * @throws NotFoundException
     * @throws GeneralException
     * @throws UnauthorizedException
     */
    public Verify getVerifyObject(String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty for verify");
        }
        return messageBirdService.requestByID(VERIFYPATH, id, Verify.class);
    }

    /**
     *
     * @param id
     * @throws NotFoundException
     * @throws GeneralException
     * @throws UnauthorizedException
     */
    public void deleteVerifyObject(String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty for verify");
        }
        messageBirdService.deleteByID(VERIFYPATH, id);
    }

    /**
     * Send a Lookup request
     *
     * @param Lookup
     * @return Lookup
     * @throws UnauthorizedException
     * @throws GeneralException
     * @throws NotFoundException
     */
    public Lookup viewLookup(final Lookup lookup) throws UnauthorizedException, GeneralException, NotFoundException {
        if (lookup.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Phonenumber must be specified.");
        }
        final Map<String, Object> params = new LinkedHashMap<String, Object>();
        if (lookup.getCountryCode() != null) {
            params.put("countryCode", lookup.getCountryCode());
        }
        return messageBirdService.requestByID(LOOKUPPATH, String.valueOf(lookup.getPhoneNumber()), params, Lookup.class);
    }

    /**
     * Send a Lookup request
     *
     * @param phonenumber
     * @return Lookup
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public Lookup viewLookup(final BigInteger phonenumber) throws UnauthorizedException, GeneralException, NotFoundException {
        if (phonenumber == null) {
            throw new IllegalArgumentException("Phonenumber must be specified.");
        }
        final Lookup lookup = new Lookup(phonenumber);
        return this.viewLookup(lookup);
    }

    /**
     * Request a Lookup HLR (lookup)
     *
     * @param LookupHlr
     * @return lookupHlr
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public LookupHlr requestLookupHlr(final LookupHlr lookupHlr) throws UnauthorizedException, GeneralException {
        if (lookupHlr.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Phonenumber must be specified.");
        }
        if (lookupHlr.getReference() == null) {
            throw new IllegalArgumentException("Reference must be specified.");
        }
        final Map<String, Object> payload = new LinkedHashMap<String, Object>();
        payload.put("phoneNumber", lookupHlr.getPhoneNumber());
        payload.put("reference", lookupHlr.getReference());
        if (lookupHlr.getCountryCode() != null) {
            payload.put("countryCode", lookupHlr.getCountryCode());
        }
        return messageBirdService.sendPayLoad(String.format(LOOKUPHLRPATH, lookupHlr.getPhoneNumber()), payload, LookupHlr.class);
    }

    /**
     * Request a Lookup HLR (lookup)
     *
     * @param phonenumber
     * @param reference
     * @return lookupHlr
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    public LookupHlr requestLookupHlr(final BigInteger phonenumber, final String reference) throws UnauthorizedException, GeneralException {
        if (phonenumber == null) {
            throw new IllegalArgumentException("Phonenumber must be specified.");
        }
        if (reference == null) {
            throw new IllegalArgumentException("Reference must be specified.");
        }
        final LookupHlr lookupHlr = new LookupHlr();
        lookupHlr.setPhoneNumber(phonenumber);
        lookupHlr.setReference(reference);
        return this.requestLookupHlr(lookupHlr);
    }

    /**
     * View a Lookup HLR (lookup)
     *
     * @param LookupHlr
     * @return LookupHlr
     * @throws UnauthorizedException
     * @throws GeneralException
     * @throws NotFoundException
     */
    public LookupHlr viewLookupHlr(final LookupHlr lookupHlr) throws UnauthorizedException, GeneralException, NotFoundException {
        if (lookupHlr.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Phonenumber must be specified");
        }
        final Map<String, Object> params = new LinkedHashMap<String, Object>();
        if (lookupHlr.getCountryCode() != null) {
            params.put("countryCode", lookupHlr.getCountryCode());
        }
        return messageBirdService.requestByID(String.format(LOOKUPHLRPATH, lookupHlr.getPhoneNumber()), "", params, LookupHlr.class);
    }

    /**
     * View a Lookup HLR (lookup)
     *
     * @param phonenumber
     * @return LookupHlr
     * @throws UnauthorizedException
     * @throws GeneralException
     * @throws NotFoundException
     */
    public LookupHlr viewLookupHlr(final BigInteger phonenumber) throws UnauthorizedException, GeneralException, NotFoundException {
        if (phonenumber == null) {
            throw new IllegalArgumentException("Phonenumber must be specified");
        }
        final LookupHlr lookupHlr = new LookupHlr();
        lookupHlr.setPhoneNumber(phonenumber);
        return this.viewLookupHlr(lookupHlr);
    }
}
