package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import com.messagebird.objects.conversations.Conversation;
import com.messagebird.objects.conversations.ConversationList;
import com.messagebird.objects.conversations.ConversationMessage;
import com.messagebird.objects.conversations.ConversationMessageList;
import com.messagebird.objects.conversations.ConversationMessageRequest;
import com.messagebird.objects.conversations.ConversationSendRequest;
import com.messagebird.objects.conversations.ConversationSendResponse;
import com.messagebird.objects.conversations.ConversationStartRequest;
import com.messagebird.objects.conversations.ConversationStatus;
import com.messagebird.objects.conversations.ConversationUpdateRequest;
import com.messagebird.objects.conversations.ConversationWebhook;
import com.messagebird.objects.conversations.ConversationWebhookCreateRequest;
import com.messagebird.objects.conversations.ConversationWebhookList;
import com.messagebird.objects.conversations.ConversationWebhookUpdateRequest;
import com.messagebird.objects.integrations.Template;
import com.messagebird.objects.integrations.TemplateList;
import com.messagebird.objects.integrations.TemplateResponse;
import com.messagebird.objects.voicecalls.RecordingResponse;
import com.messagebird.objects.voicecalls.TranscriptionResponse;
import com.messagebird.objects.voicecalls.VoiceCall;
import com.messagebird.objects.voicecalls.VoiceCallFlowList;
import com.messagebird.objects.voicecalls.VoiceCallFlowRequest;
import com.messagebird.objects.voicecalls.VoiceCallFlowResponse;
import com.messagebird.objects.voicecalls.VoiceCallLeg;
import com.messagebird.objects.voicecalls.VoiceCallLegResponse;
import com.messagebird.objects.voicecalls.VoiceCallResponse;
import com.messagebird.objects.voicecalls.VoiceCallResponseList;
import com.messagebird.objects.voicecalls.Webhook;
import com.messagebird.objects.voicecalls.WebhookList;
import com.messagebird.objects.voicecalls.WebhookResponseData;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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

    /**
     * The Conversations API has a different URL scheme from the other
     * APIs/endpoints. By default, the service prefixes paths with that URL. We
     * can, however, override this behaviour by providing absolute URLs
     * ourselves.
     */
    static final String CONVERSATIONS_BASE_URL = "https://conversations.messagebird.com/v1";
    static final String VOICE_CALLS_BASE_URL = "https://voice.messagebird.com";
    static final String NUMBERS_CALLS_BASE_URL = "https://numbers.messagebird.com/v1";
    static final String MESSAGING_BASE_URL = "https://messaging.messagebird.com/v1";
    static final String INTEGRATIONS_BASE_URL_V2 = "https://integrations.messagebird.com/v2";
    static final String INTEGRATIONS_BASE_URL_V3 = "https://integrations.messagebird.com/v3";
    private static String[] supportedLanguages = {"de-DE", "en-AU", "en-UK", "en-US", "es-ES", "es-LA", "fr-FR", "it-IT", "nl-NL", "pt-BR"};
    static final String PARTNER_ACCOUNTS_BASE_URL = "https://partner-accounts.messagebird.com";

    private static final String BALANCEPATH = "/balance";
    private static final String CONTACTPATH = "/contacts";
    private static final String GROUPPATH = "/groups";
    private static final String HLRPATH = "/hlr";
    private static final String LOOKUPHLRPATH = "/lookup/%s/hlr";
    private static final String LOOKUPPATH = "/lookup";
    private static final String MESSAGESPATH = "/messages";
    private static final String VERIFYPATH = "/verify";
    private static final String VERIFYEMAILPATH = "/verify/messages/email";
    private static final String VOICEMESSAGESPATH = "/voicemessages";
    static final String CONVERSATION_PATH = "/conversations";
    static final String CONVERSATION_SEND_PATH = "/send";
    static final String CONVERSATION_MESSAGE_PATH = "/messages";
    static final String CONVERSATION_WEBHOOK_PATH = "/webhooks";
    static final String INTEGRATIONS_WHATSAPP_PATH = "/platforms/whatsapp";
    static final String VOICECALLSPATH = "/calls";
    static final String LEGSPATH = "/legs";
    static final String RECORDINGPATH = "/recordings";
    static final String TRANSCRIPTIONPATH = "/transcriptions";
    static final String WEBHOOKS = "/webhooks";
    static final String VOICECALLFLOWPATH = "/call-flows";
    private static final String VOICELEGS_SUFFIX_PATH = "/legs";
    static final String FILES_PATH = "/files";
    static final String TEMPLATES_PATH = "/templates";
    static final String UNPAUSE_TEMAPLATE_PATH = "/unpause";
    static final String OUTBOUND_SMS_PRICING_PATH = "/pricing/sms/outbound";
    static final String OUTBOUND_SMS_PRICING_SMPP_PATH = "/pricing/sms/outbound/smpp/%s";

    static final String RECORDING_DOWNLOAD_FORMAT = ".wav";

    static final String TRANSCRIPTION_DOWNLOAD_FORMAT = ".txt";
  
    private static final int DEFAULT_MACHINE_TIMEOUT_VALUE = 7000;
    private static final int MIN_MACHINE_TIMEOUT_VALUE = 400;
    private static final int MAX_MACHINE_TIMEOUT_VALUE = 10000;

    private static final String[] MESSAGE_LIST_FILTERS_VALS = {"originator", "recipient", "direction", "searchterm", "type", "contact_id", "status", "from", "until"};
    private static final Set<String> MESSAGE_LIST_FILTERS = new HashSet<>(Arrays.asList(MESSAGE_LIST_FILTERS_VALS));

    private static final String[] CONVERSATION_MESSAGE_LIST_FILTERS_VALS = {"ids", "from"};
    private static final Set<String> CONVERSATION_MESSAGE_LIST_FILTERS = new HashSet<>(Arrays.asList(CONVERSATION_MESSAGE_LIST_FILTERS_VALS));

    private final String DOWNLOADS = "Downloads";

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
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @return MessageResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @return MessageResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @return MessageResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public MessageResponse sendFlashMessage(final String originator, final String body, final List<BigInteger> recipients, final String reference) throws UnauthorizedException, GeneralException {
        final Message message = new Message(originator, body, recipients);
        message.setType(MsgType.flash);
        message.setReference(reference);
        return messageBirdService.sendPayLoad(MESSAGESPATH, message, MessageResponse.class);
    }

    public MessageList listMessages(final Integer offset, final Integer limit) throws UnauthorizedException, GeneralException {
        verifyOffsetAndLimit(offset, limit);
        return messageBirdService.requestList(MESSAGESPATH, offset, limit, MessageList.class);
    }

    public MessageList listMessagesFiltered(final Integer offset, final Integer limit, final Map<String, Object> filters) throws UnauthorizedException, GeneralException {
        verifyOffsetAndLimit(offset, limit);

        for (String filter : filters.keySet()) {
            if (!MESSAGE_LIST_FILTERS.contains(filter)) {
                throw new IllegalArgumentException("Invalid filter name: " + filter);
            }
        }

        return messageBirdService.requestList(MESSAGESPATH, filters, offset, limit, MessageList.class);
    }

    /**
     * Delete a message from the Messagebird server
     *
     * @param id A unique random ID which is created on the MessageBird platform
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @param id A unique random ID which is created on the MessageBird platform
     * @return MessageResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @return VoiceMessageResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VoiceMessageResponse sendVoiceMessage(final VoiceMessage voiceMessage) throws UnauthorizedException, GeneralException {
        addDefaultMachineTimeoutValueIfNotExists(voiceMessage);
        checkMachineTimeoutValueIsInRange(voiceMessage);
        return messageBirdService.sendPayLoad(VOICEMESSAGESPATH, voiceMessage, VoiceMessageResponse.class);
    }

    /**
     * Convenient function to send a simple message to a list of recipients
     *
     * @param body       Body of the message
     * @param recipients List of recipients
     * @return VoiceMessageResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VoiceMessageResponse sendVoiceMessage(final String body, final List<BigInteger> recipients) throws UnauthorizedException, GeneralException {
        final VoiceMessage message = new VoiceMessage(body, recipients);
        addDefaultMachineTimeoutValueIfNotExists(message);
        checkMachineTimeoutValueIsInRange(message);
        return messageBirdService.sendPayLoad(VOICEMESSAGESPATH, message, VoiceMessageResponse.class);
    }

    /**
     * Convenient function to send a simple message to a list of recipients
     *
     * @param body       Body of the message
     * @param recipients List of recipients
     * @param reference  your reference
     * @return VoiceMessageResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VoiceMessageResponse sendVoiceMessage(final String body, final List<BigInteger> recipients, final String reference) throws UnauthorizedException, GeneralException {
        final VoiceMessage message = new VoiceMessage(body, recipients);
        message.setReference(reference);
        addDefaultMachineTimeoutValueIfNotExists(message);
        checkMachineTimeoutValueIsInRange(message);
        return messageBirdService.sendPayLoad(VOICEMESSAGESPATH, message, VoiceMessageResponse.class);
    }

    private void addDefaultMachineTimeoutValueIfNotExists(final VoiceMessage voiceMessage){
        if (voiceMessage.getMachineTimeout() == 0){
            voiceMessage.setMachineTimeout(DEFAULT_MACHINE_TIMEOUT_VALUE); //default machine timeout value
        }
    }

    private void checkMachineTimeoutValueIsInRange(final VoiceMessage voiceMessage){
        if (voiceMessage.getMachineTimeout() < MIN_MACHINE_TIMEOUT_VALUE ||  voiceMessage.getMachineTimeout() > MAX_MACHINE_TIMEOUT_VALUE){
            throw new IllegalArgumentException("Please define machine timeout value between " + MIN_MACHINE_TIMEOUT_VALUE + " and " + MAX_MACHINE_TIMEOUT_VALUE);
        }
    }

    /**
     * Delete a voice message from the Messagebird server
     *
     * @param id A unique random ID which is created on the MessageBird platform
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @param id A unique random ID which is created on the MessageBird platform
     * @return VoiceMessageResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @param offset offset for result list
     * @param limit  limit for result list
     * @return VoiceMessageList
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VoiceMessageList listVoiceMessages(final Integer offset, final Integer limit) throws UnauthorizedException, GeneralException {
        verifyOffsetAndLimit(offset, limit);
        return messageBirdService.requestList(VOICEMESSAGESPATH, offset, limit, VoiceMessageList.class);
    }

    /**
     * @param verifyRequest includes recipient, originator, reference, type, datacoding, template, timeout, tokenLenght, voice, language
     * @return Verify object
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @param recipient The telephone number that you want to verify.
     * @return Verify object
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public Verify sendVerifyToken(String recipient) throws UnauthorizedException, GeneralException {
        if (recipient == null || recipient.isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be empty for verify");
        }
        VerifyRequest verifyRequest = new VerifyRequest(recipient);
        return this.sendVerifyToken(verifyRequest);
    }

    /**
     * @param id    A unique random ID which is created on the MessageBird platform
     * @param token An unique token which was sent to the recipient upon creation of the object.
     * @return Verify object
     * @throws NotFoundException     if id is not found
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @param id id is for getting verify object
     * @return Verify object
     * @throws NotFoundException     if id is not found
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public Verify getVerifyObject(String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty for verify");
        }
        return messageBirdService.requestByID(VERIFYPATH, id, Verify.class);
    }

    /**
     * @param messageId is for the email message part of a verify object
     * @return Verify object
     * @throws NotFoundException     if id is not found
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VerifyMessage getVerifyEmailMessage(String messageId) throws UnauthorizedException, GeneralException, NotFoundException {
        if (messageId == null || messageId.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty for verify email message");
        }
        return messageBirdService.requestByID(VERIFYEMAILPATH, messageId, VerifyMessage.class);
	}

    /**
     * @param id id for deleting verify object
     * @throws NotFoundException     if id is not found
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
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
     * @param lookup including with country code, country prefix, phone number, type, formats, country code
     * @return Lookup
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     * @throws NotFoundException     if lookup not found
     */
    public Lookup viewLookup(final Lookup lookup) throws UnauthorizedException, GeneralException, NotFoundException {
        if (lookup.getPhoneNumber() == null) {
            throw new IllegalArgumentException("PhoneNumber must be specified.");
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
     * @param phoneNumber phone number is for viewing lookup
     * @return Lookup
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public Lookup viewLookup(final BigInteger phoneNumber) throws UnauthorizedException, GeneralException, NotFoundException {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("PhoneNumber must be specified.");
        }
        final Lookup lookup = new Lookup(phoneNumber);
        return this.viewLookup(lookup);
    }

    /**
     * Request a Lookup HLR (lookup)
     *
     * @param lookupHlr country code for request lookup hlr
     * @return lookupHlr
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public LookupHlr requestLookupHlr(final LookupHlr lookupHlr) throws UnauthorizedException, GeneralException {
        if (lookupHlr.getPhoneNumber() == null) {
            throw new IllegalArgumentException("PhoneNumber must be specified.");
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
     * @param phoneNumber phone number is for request hlr
     * @param reference   reference for request hlr
     * @return lookupHlr
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public LookupHlr requestLookupHlr(final BigInteger phoneNumber, final String reference) throws UnauthorizedException, GeneralException {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("Phonenumber must be specified.");
        }
        if (reference == null) {
            throw new IllegalArgumentException("Reference must be specified.");
        }
        final LookupHlr lookupHlr = new LookupHlr();
        lookupHlr.setPhoneNumber(phoneNumber);
        lookupHlr.setReference(reference);
        return this.requestLookupHlr(lookupHlr);
    }

    /**
     * View a Lookup HLR (lookup)
     *
     * @param lookupHlr search with country code
     * @return LookupHlr
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     * @throws NotFoundException     if there is no lookup hlr with given country code
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
     * @param phoneNumber phone number for searching on hlr
     * @return LookupHlr
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     * @throws NotFoundException     phone number not found on hlr
     */
    public LookupHlr viewLookupHlr(final BigInteger phoneNumber) throws UnauthorizedException, GeneralException, NotFoundException {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("phoneNumber must be specified");
        }
        final LookupHlr lookupHlr = new LookupHlr();
        lookupHlr.setPhoneNumber(phoneNumber);
        return this.viewLookupHlr(lookupHlr);
    }

    /**
     * Convenient function to list all call flows 
     *
     * @param offset     
     * @param limit       
     * @return VoiceCallFlowList
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VoiceCallFlowList listVoiceCallFlows(final Integer offset, final Integer limit) 
        throws UnauthorizedException, GeneralException {
        verifyOffsetAndLimit(offset, limit);
        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, VOICECALLFLOWPATH);

        return messageBirdService.requestList(url, offset, limit, VoiceCallFlowList.class);
    }

    /**
     * Retrieves the information of an existing Call Flow. You only need to supply
     * the unique call flow ID that was returned upon creation or receiving.
     * @param id String
     * @return VoiceCallFlowResponse
     * @throws NotFoundException
     * @throws GeneralException
     * @throws UnauthorizedException
     */
    public VoiceCallFlowResponse viewVoiceCallFlow(final String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Call Flow ID must be specified.");
        }
        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, VOICECALLFLOWPATH);

        return messageBirdService.requestByID(url, id, VoiceCallFlowResponse.class);
    }

    /**
     * Convenient function to create a call flow 
     *
     * @param voiceCallFlowRequest VoiceCallFlowRequest
     * @return VoiceCallFlowResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VoiceCallFlowResponse sendVoiceCallFlow(final VoiceCallFlowRequest voiceCallFlowRequest)
        throws UnauthorizedException, GeneralException {
        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, VOICECALLFLOWPATH);

        return messageBirdService.sendPayLoad(url, voiceCallFlowRequest, VoiceCallFlowResponse.class);
    }

    /**
     * Updates an existing Call Flow. You only need to supply the unique id that
     * was returned upon creation.
     * @param id String
     * @param voiceCallFlowRequest VoiceCallFlowRequest
     * @return VoiceCallFlowResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VoiceCallFlowResponse updateVoiceCallFlow(String id, VoiceCallFlowRequest voiceCallFlowRequest)
        throws UnauthorizedException, GeneralException {
        if (id == null) {
            throw new IllegalArgumentException("Call Flow ID must be specified.");
        }
        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, VOICECALLFLOWPATH);
        String request = url + "/" + id;

        return messageBirdService.sendPayLoad("PUT", request, voiceCallFlowRequest, VoiceCallFlowResponse.class);
    }
    
    /**
     * Convenient function to delete call flow 
     *
     * @param id String
     * @return void
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public void deleteVoiceCallFlow(final String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Voice Call Flow ID must be specified.");
        }
        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, VOICECALLFLOWPATH);
        messageBirdService.deleteByID(url, id);
    }

    /**
     * Deletes an existing contact. You only need to supply the unique id that
     * was returned upon creation.
     */
    void deleteContact(final String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Contact ID must be specified.");
        }
        messageBirdService.deleteByID(CONTACTPATH, id);
    }

    /**
     * Gets a contact listing with specified pagination options.
     *
     * @return Listing of Contact objects.
     */
    public ContactList listContacts(int offset, int limit) throws UnauthorizedException, GeneralException {
        return messageBirdService.requestList(CONTACTPATH, offset, limit, ContactList.class);
    }

    /**
     * Gets a contact listing with default pagination options.
     */
    public ContactList listContacts() throws UnauthorizedException, GeneralException {
        final int limit = 20;
        final int offset = 0;

        return listContacts(offset, limit);
    }

    /**
     * Creates a new contact object. MessageBird returns the created contact
     * object with each request.
     */
    public Contact sendContact(final ContactRequest contactRequest) throws UnauthorizedException, GeneralException {
        return messageBirdService.sendPayLoad(CONTACTPATH, contactRequest, Contact.class);
    }

    /**
     * Updates an existing contact. You only need to supply the unique id that
     * was returned upon creation.
     */
    public Contact updateContact(final String id, ContactRequest contactRequest) throws UnauthorizedException, GeneralException {
        if (id == null) {
            throw new IllegalArgumentException("Contact ID must be specified.");
        }
        String request = CONTACTPATH + "/" + id;
        return messageBirdService.sendPayLoad("PATCH", request, contactRequest, Contact.class);
    }

    /**
     * Retrieves the information of an existing contact. You only need to supply
     * the unique contact ID that was returned upon creation or receiving.
     */
    public Contact viewContact(final String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Contact ID must be specified.");
        }
        return messageBirdService.requestByID(CONTACTPATH, id, Contact.class);
    }

    /**
     * Deletes an existing group. You only need to supply the unique id that
     * was returned upon creation.
     */
    public void deleteGroup(final String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Group ID must be specified.");
        }
        messageBirdService.deleteByID(GROUPPATH, id);
    }

    /**
     * Removes a contact from group. You need to supply the IDs of the group
     * and contact. Does not delete the contact.
     */
    public void deleteGroupContact(final String groupId, final String contactId) throws NotFoundException, GeneralException, UnauthorizedException {
        if (groupId == null) {
            throw new IllegalArgumentException("Group ID must be specified.");
        }
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID must be specified.");
        }

        String id = String.format("%s%s/%s", groupId, CONTACTPATH, contactId);
        messageBirdService.deleteByID(GROUPPATH, id);
    }

    /**
     * Gets a contact listing with specified pagination options.
     */
    public GroupList listGroups(final int offset, final int limit) throws UnauthorizedException, GeneralException {
        return messageBirdService.requestList(GROUPPATH, offset, limit, GroupList.class);
    }

    /**
     * Gets a contact listing with default pagination options.
     */
    public GroupList listGroups() throws UnauthorizedException, GeneralException {
        final int offset = 0;
        final int limit = 20;

        return listGroups(offset, limit);
    }

    /**
     * Creates a new group object. MessageBird returns the created group object
     * with each request.
     */
    public Group sendGroup(final GroupRequest groupRequest) throws UnauthorizedException, GeneralException {
        return messageBirdService.sendPayLoad(GROUPPATH, groupRequest, Group.class);
    }

    /**
     * Adds contact to group. You need to supply the IDs of the group and
     * contact.
     */
    public void sendGroupContact(final String groupId, final String[] contactIds) throws NotFoundException, GeneralException, UnauthorizedException {
        // reuestByID appends the "ID" to the base path, so this workaround
        // lets us add a query string.
        String path = String.format("%s%s?%s", groupId, CONTACTPATH, getQueryString(contactIds));
        messageBirdService.requestByID(GROUPPATH, path, null);
    }

    /**
     * Builds a query string to add contacts to a group. We're using the
     * alternative "/foo?_method=PUT&ids[]=foo&ids[]=bar" format to send the
     * contact IDs as GET params. Sending these in the request body would
     * require a painful workaround, as sendPayload sends request bodies as
     * JSON by default. See also:
     * https://developers.messagebird.com/docs/alternatives.
     */
    private String getQueryString(final String[] contactIds) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_method=PUT");

        for (String groupId : contactIds) {
            stringBuilder.append("&ids[]=").append(groupId);
        }

        return stringBuilder.toString();
    }

    /**
     * Updates an existing group. You only need to supply the unique ID that
     * was returned upon creation.
     */
    public Group updateGroup(final String id, final GroupRequest groupRequest) throws UnauthorizedException, GeneralException {
        if (id == null) {
            throw new IllegalArgumentException("Group ID must be specified.");
        }
        String path = String.format("%s/%s", GROUPPATH, id);
        return messageBirdService.sendPayLoad("PATCH", path, groupRequest, Group.class);
    }

    /**
     * Retrieves the information of an existing group. You only need to supply
     * the unique group ID that was returned upon creation or receiving.
     */
    public Group viewGroup(final String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Group ID must be specified.");
        }
        return messageBirdService.requestByID(GROUPPATH, id, Group.class);
    }

    /**
     * Gets a single conversation.
     *
     * @param id Conversation to retrieved.
     * @return The retrieved conversation.
     */
    public Conversation viewConversation(final String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Id must be specified");
        }
        String url = CONVERSATIONS_BASE_URL + CONVERSATION_PATH;
        return messageBirdService.requestByID(url, id, Conversation.class);
    }

    /**
     * Updates a conversation.
     *
     * @param id     Conversation to update.
     * @param status New status for the conversation.
     * @return The updated Conversation.
     */
    public Conversation updateConversation(final String id, final ConversationStatus status)
            throws UnauthorizedException, GeneralException {
        if (id == null) {
            throw new IllegalArgumentException("Id must be specified.");
        }
        if (status == null) {
            throw new IllegalArgumentException("An updated conversation status must be specified");
        }
        ConversationUpdateRequest payload = new ConversationUpdateRequest(status);
        String url = String.format("%s%s/%s", CONVERSATIONS_BASE_URL, CONVERSATION_PATH, id);
        return messageBirdService.sendPayLoad("PATCH", url, payload, Conversation.class);
    }

    /**
     * Gets a Conversation listing with specified pagination options.
     *
     * @param offset Number of objects to skip.
     * @param limit  Number of objects to take.
     * @return List of conversations.
     */
    public ConversationList listConversations(final int offset, final int limit)
            throws UnauthorizedException, GeneralException {
        String url = CONVERSATIONS_BASE_URL + CONVERSATION_PATH;
        return messageBirdService.requestList(url, offset, limit, ConversationList.class);
    }

    /**
     * Gets a contact listing with default pagination options.
     *
     * @return List of conversations.
     */
    public ConversationList listConversations() throws UnauthorizedException, GeneralException {
        final int offset = 0;
        final int limit = 10;

        return listConversations(offset, limit);
    }

    /**
     * Starts a conversation by sending an initial message.
     *
     * @param request Data for this request.
     * @return The created Conversation.
     */
    public Conversation startConversation(ConversationStartRequest request)
            throws UnauthorizedException, GeneralException {
        String url = String.format("%s%s/start", CONVERSATIONS_BASE_URL, CONVERSATION_PATH);
        return messageBirdService.sendPayLoad(url, request, Conversation.class);
    }

    /**
     * sendMessage allows you to send message to users over any communication platform supported by Programmable Conversations
     *
     * @param request Data for this request.
     * @return The created Message in ConversationSendResponse object.
     */
    public ConversationSendResponse sendMessage(ConversationSendRequest request)
            throws UnauthorizedException, GeneralException {
        String url = String.format("%s%s", CONVERSATIONS_BASE_URL, CONVERSATION_SEND_PATH);
        return messageBirdService.sendPayLoad(url, request, ConversationSendResponse.class);
    }

    /**
     * Gets a ConversationMessage listing with specified pagination options.
     *
     * @param conversationId Conversation to get messages for.
     * @param offset         Number of objects to skip.
     * @param limit          Number of objects to take.
     * @return List of messages.
     */
    public ConversationMessageList listConversationMessages(
            final String conversationId,
            final int offset,
            final int limit
    ) throws UnauthorizedException, GeneralException {
        String url = String.format(
                "%s%s/%s%s",
                CONVERSATIONS_BASE_URL,
                CONVERSATION_PATH,
                conversationId,
                CONVERSATION_MESSAGE_PATH
        );
        return messageBirdService.requestList(url, offset, limit, ConversationMessageList.class);
    }

    /**
     * Gets a ConversationMessage listing with default pagination options.
     *
     * @param conversationId Conversation to get messages for.
     * @return List of messages.
     */
    public ConversationMessageList listConversationMessages(
            final String conversationId
    ) throws UnauthorizedException, GeneralException {
        final int offset = 0;
        final int limit = 10;

        return listConversationMessages(conversationId, offset, limit);
    }

    /**
     * Gets a single message.
     *
     * @param messageId Message to retrieve.
     * @return The retrieved message.
     */
    public ConversationMessage viewConversationMessage(final String messageId)
            throws NotFoundException, GeneralException, UnauthorizedException {
        String url = CONVERSATIONS_BASE_URL + CONVERSATION_MESSAGE_PATH;
        return messageBirdService.requestByID(url, messageId, ConversationMessage.class);
    }

    /**
     * Gets conversation messages based on query param.
     *
     * @param queryParams only `ids` and `from` is available as an option
     * @return The retrieved messages.
     */
    public ConversationMessageList listConversationMessagesWithQueryParam(Map<String, Object> queryParams)
        throws NotFoundException, GeneralException, UnauthorizedException {
        for (String queryParam : queryParams.keySet()) {
            if (!CONVERSATION_MESSAGE_LIST_FILTERS.contains(queryParam)) {
                throw new IllegalArgumentException("Invalid filter name: " + queryParam);
            }
        }
        String url = CONVERSATIONS_BASE_URL + CONVERSATION_MESSAGE_PATH;
        return messageBirdService.requestByID(url, null, queryParams, ConversationMessageList.class);
    }

    /**
     * Sends a message to an existing Conversation.
     *
     * @param conversationId Conversation to send message to.
     * @param request        Message to send.
     * @return The newly created message.
     */
    public ConversationMessage sendConversationMessage(
            final String conversationId,
            final ConversationMessageRequest request
    ) throws UnauthorizedException, GeneralException {
        String url = String.format(
                "%s%s/%s%s",
                CONVERSATIONS_BASE_URL,
                CONVERSATION_PATH,
                conversationId,
                CONVERSATION_MESSAGE_PATH
        );
        return messageBirdService.sendPayLoad(url, request, ConversationMessage.class);
    }

    /**
     * Deletes a webhook.
     *
     * @param webhookId Webhook to delete.
     */
    public void deleteConversationWebhook(final String webhookId)
            throws NotFoundException, GeneralException, UnauthorizedException {
        String url = CONVERSATIONS_BASE_URL + CONVERSATION_WEBHOOK_PATH;
        messageBirdService.deleteByID(url, webhookId);
    }

    /**
     * Creates a new webhook.
     *
     * @param request Webhook to create.
     * @return Newly created webhook.
     */
    public ConversationWebhook sendConversationWebhook(final ConversationWebhookCreateRequest request)
            throws UnauthorizedException, GeneralException {
        String url = CONVERSATIONS_BASE_URL + CONVERSATION_WEBHOOK_PATH;
        return messageBirdService.sendPayLoad(url, request, ConversationWebhook.class);
    }

    /**
     * Update an existing webhook.
     *
     * @param request update request.
     * @return Updated webhook.
     */
    public ConversationWebhook updateConversationWebhook(final String id, final ConversationWebhookUpdateRequest request) throws UnauthorizedException, GeneralException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Conversation webhook ID must be specified.");
        }

        String url = CONVERSATIONS_BASE_URL + CONVERSATION_WEBHOOK_PATH + "/" + id;
        return messageBirdService.sendPayLoad("PATCH", url, request, ConversationWebhook.class);
    }

    /**
     * Gets a single webhook.
     *
     * @param webhookId Webhook to retrieve.
     * @return The retrieved webhook.
     */
    public ConversationWebhook viewConversationWebhook(final String webhookId) throws NotFoundException, GeneralException, UnauthorizedException {
        String url = CONVERSATIONS_BASE_URL + CONVERSATION_WEBHOOK_PATH;
        return messageBirdService.requestByID(url, webhookId, ConversationWebhook.class);
    }

    /**
     * Gets a ConversationWebhook listing with the specified pagination options.
     *
     * @param offset Number of objects to skip.
     * @param limit  Number of objects to skip.
     * @return List of webhooks.
     */
    public ConversationWebhookList listConversationWebhooks(final int offset, final int limit)
            throws UnauthorizedException, GeneralException {
        String url = CONVERSATIONS_BASE_URL + CONVERSATION_WEBHOOK_PATH;
        return messageBirdService.requestList(url, offset, limit, ConversationWebhookList.class);
    }

    /**
     * Gets a ConversationWebhook listing with default pagination options.
     *
     * @return List of webhooks.
     */
    public ConversationWebhookList listConversationWebhooks() throws UnauthorizedException, GeneralException {
        final int offset = 0;
        final int limit = 10;

        return listConversationWebhooks(offset, limit);
    }

    /****************************************************************************************************/
    /** Voice Calling
     /****************************************************************************************************/

    /**
     * Function for voice call to a number
     *
     * @param voiceCall Voice call object
     * @return VoiceCallResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VoiceCallResponse sendVoiceCall(final VoiceCall voiceCall) throws UnauthorizedException, GeneralException {
        if (voiceCall.getSource() == null) {
            throw new IllegalArgumentException("Source of voice call must be specified.");
        }
        if (voiceCall.getDestination() == null) {
            throw new IllegalArgumentException("Destination of voice call must be specified.");
        }
        if (voiceCall.getCallFlow() == null) {
            throw new IllegalArgumentException("Call flow of voice call must be specified.");
        }

        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, VOICECALLSPATH);

        return messageBirdService.sendPayLoad(url, voiceCall, VoiceCallResponse.class);
    }

    /**
     * Function to list all voice calls
     *
     * @return VoiceCallResponseList
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VoiceCallResponseList listAllVoiceCalls(Integer page, Integer pageSize) throws GeneralException, UnauthorizedException {
        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, VOICECALLSPATH);
        return messageBirdService.requestList(url, new PagedPaging(page, pageSize), VoiceCallResponseList.class);
    }

    /**
     * Function to view voice call by id
     *
     * @param id Voice call ID
     * @return VoiceCallResponse
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public VoiceCallResponse viewVoiceCall(final String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Voice Message ID must be specified.");
        }

        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, VOICECALLSPATH);
        return messageBirdService.requestByID(url, id, VoiceCallResponse.class);
    }

    /**
     * Function to delete voice call by id
     *
     * @param id Voice call ID
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public void deleteVoiceCall(final String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Voice Message ID must be specified.");
        }

        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, VOICECALLSPATH);
        messageBirdService.deleteByID(url, id);
    }

    /**
     * Retrieves a listing of all legs.
     *
     * @param callId   Voice call ID
     * @param page     page to fetch (can be null - will return first page), number of first page is 1
     * @param pageSize page size
     * @return VoiceCallLegResponse
     * @throws UnsupportedEncodingException no UTF8 supported url
     * @throws UnauthorizedException        if client is unauthorized
     * @throws GeneralException             general exception
     */
    public VoiceCallLegResponse viewCallLegsByCallId(String callId, Integer page, Integer pageSize) throws UnsupportedEncodingException, UnauthorizedException, GeneralException {
        if (callId == null) {
            throw new IllegalArgumentException("Voice call ID must be specified.");
        }

        String url = String.format(
                "%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                urlEncode(callId),
                VOICELEGS_SUFFIX_PATH);

        return messageBirdService.requestList(url, new PagedPaging(page, pageSize), VoiceCallLegResponse.class);
    }

    /**
     * Retrieves a leg resource.
     * The parameters are the unique ID of the call and of the leg that were returned upon their respective creation.
     *
     * @param callId Voice call ID
     * @param legId  ID of leg of specified call {callId}
     * @return VoiceCallLeg
     * @throws UnsupportedEncodingException no UTF8 supported url
     * @throws NotFoundException            not found with callId and legId
     * @throws UnauthorizedException        if client is unauthorized
     * @throws GeneralException             general exception
     */
    public VoiceCallLeg viewCallLegByCallIdAndLegId(final String callId, String legId) throws UnsupportedEncodingException, NotFoundException, GeneralException, UnauthorizedException {
        if (callId == null) {
            throw new IllegalArgumentException("Voice call ID must be specified.");
        }

        if (legId == null) {
            throw new IllegalArgumentException("Leg ID must be specified.");
        }

        String url = String.format(
                "%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                urlEncode(callId),
                VOICELEGS_SUFFIX_PATH);

        VoiceCallLegResponse response = messageBirdService.requestByID(url, legId, VoiceCallLegResponse.class);

        if (response.getData().size() == 1) {
            return response.getData().get(0);
        } else {
            throw new NotFoundException("No such leg", new LinkedList<ErrorReport>());
        }
    }

    private String urlEncode(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, String.valueOf(StandardCharsets.UTF_8));
    }

    /**
     * Function to view recording by call id , leg id and recording id
     *
     * @param callID      Voice call ID
     * @param legId       Leg ID
     * @param recordingId Recording ID
     * @return Recording
     * @throws NotFoundException     not found with callId and legId
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public RecordingResponse viewRecording(String callID, String legId, String recordingId) throws NotFoundException, GeneralException, UnauthorizedException {

        if (callID == null) {
            throw new IllegalArgumentException("Voice call ID must be specified.");
        }

        if (legId == null) {
            throw new IllegalArgumentException("Leg ID must be specified.");
        }

        if (recordingId == null) {
            throw new IllegalArgumentException("Recording ID must be specified.");
        }

        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, VOICECALLSPATH);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("legs", legId);
        params.put("recordings", recordingId);

        return messageBirdService.requestByID(url, callID, params, RecordingResponse.class);
    }

    /**
     * Downloads the record in .wav format by using callId, legId and recordId and stores to basePath. basePath is not mandatory to set.
     * If basePath is not set, default download will be the /Download folder in user group.
     *
     * @param callID Voice call ID
     * @param legId Leg ID
     * @param recordingId Recording ID
     * @param basePath store location. It should be directory. Property is Optional if $HOME is accessible
     * @return the path that file is stored
     * @throws NotFoundException if the recording does not found
     * @throws GeneralException general exception
     * @throws UnauthorizedException if client is unauthorized
     */
    public String downloadRecording(String callID, String legId, String recordingId, String basePath) throws NotFoundException, GeneralException, UnauthorizedException {

        if (callID == null) {
            throw new IllegalArgumentException("Voice call ID must be specified.");
        }

        if (legId == null) {
            throw new IllegalArgumentException("Leg ID must be specified.");
        }

        if (recordingId == null) {
            throw new IllegalArgumentException("Recording ID must be specified.");
        }

        String url = String.format(
                "%s%s/%s%s/%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                callID,
                LEGSPATH,
                legId,
                RECORDINGPATH,
                recordingId,
                RECORDING_DOWNLOAD_FORMAT
                );
        String fileName = String.format("%s%s",recordingId, RECORDING_DOWNLOAD_FORMAT);
        return messageBirdService.getBinaryData(url, basePath, fileName);
    }

    /**
     * List the all recordings related to CallID and LegId
     *
     * @param callID Voice call ID
     * @param legId Leg ID
     * @param offset Number of objects to skip.
     * @param limit  Number of objects to take.
     * @return Recordings for CallID and LegID
     * @throws GeneralException if client is unauthorized
     * @throws UnauthorizedException general exception
     */
    public RecordingResponse listRecordings(String callID, String legId, final Integer offset, final Integer limit)
            throws GeneralException, UnauthorizedException {
        verifyOffsetAndLimit(offset, limit);
        if (callID == null) {
            throw new IllegalArgumentException("Voice call ID must be specified.");
        }

        if (legId == null) {
            throw new IllegalArgumentException("Leg ID must be specified.");
        }

        String url = String.format(
                "%s%s/%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                callID,
                LEGSPATH,
                legId,
                RECORDINGPATH);

        return messageBirdService.requestList(url, offset, limit, RecordingResponse.class);
    }

    /**
     * Deletes a voice recording
     *
     * @param callID Voice call ID
     * @param legID Leg ID
     * @param recordingID Recording ID
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public void deleteRecording(final String callID, final String legID, final String recordingID)
            throws NotFoundException, GeneralException, UnauthorizedException {
        if (callID == null) {
            throw new IllegalArgumentException("Call ID must be specified.");
        }
        if (legID == null) {
            throw new IllegalArgumentException("Leg ID must be specified.");
        }
        if (recordingID == null) {
            throw new IllegalArgumentException("Recording ID must be specified.");
        }
        String url = String.format(
                "%s%s/%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                callID,
                LEGSPATH,
                legID,
                RECORDINGPATH
        );
        messageBirdService.deleteByID(url, recordingID);
    }

    /**
     * Function to view recording by call id , leg id and recording id
     *
     * @param callID      Voice call ID
     * @param legId       Leg ID
     * @param recordingId Recording ID
     * @param language    Language
     * @return TranscriptionResponseList
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public TranscriptionResponse createTranscription(String callID, String legId, String recordingId, String language)
            throws UnauthorizedException, GeneralException {
        if (callID == null) {
            throw new IllegalArgumentException("Voice call ID must be specified.");
        }

        if (legId == null) {
            throw new IllegalArgumentException("Leg ID must be specified.");
        }

        if (recordingId == null) {
            throw new IllegalArgumentException("Recording ID must be specified.");
        }

        if (language == null) {
            throw new IllegalArgumentException("Language must be specified.");
        }

        if (!Arrays.asList(supportedLanguages).contains(language)) {
            throw new IllegalArgumentException("Your language is not allowed.");
        }

        String url = String.format(
                "%s%s/%s%s/%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                callID,
                LEGSPATH,
                legId,
                RECORDINGPATH,
                recordingId,
                TRANSCRIPTIONPATH);

        return messageBirdService.sendPayLoad(url, language, TranscriptionResponse.class);
    }

    /**
     * Function to view recording by call id, leg id and recording id
     *
     * @param callID      Voice call ID
     * @param legId       Leg ID
     * @param recordingId Recording ID
     * @param transcriptionId Transcription ID
     * @return Transcription
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     * @throws NotFoundException If transcription is not found
     */
    public TranscriptionResponse viewTranscription(String callID, String legId, String recordingId, String transcriptionId) throws UnauthorizedException, GeneralException, NotFoundException {
        if (callID == null) {
            throw new IllegalArgumentException("Voice call ID must be specified.");
        }

        if (legId == null) {
            throw new IllegalArgumentException("Leg ID must be specified.");
        }

        if (recordingId == null) {
            throw new IllegalArgumentException("Recording ID must be specified.");
        }

        if(transcriptionId == null) {
            throw new IllegalArgumentException("Transcription ID must be specified.");
        }

        String url = String.format(
                "%s%s/%s%s/%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                callID,
                LEGSPATH,
                legId,
                RECORDINGPATH,
                recordingId,
                TRANSCRIPTIONPATH);

        return messageBirdService.requestByID(url, transcriptionId, TranscriptionResponse.class);
    }

    /**
     * Lists the Transcription of callId, legId and recordId
     *
     * @param callID Voice call ID
     * @param legId Leg ID
     * @param recordingId Recording ID
     * @param page     page to fetch (can be null - will return first page), number of first page is 1
     * @param pageSize page size
     * @return List of Transcription
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException general exception
     */
    public TranscriptionResponse listTranscriptions(String callID, String legId, String recordingId, Integer page, Integer pageSize) throws UnauthorizedException, GeneralException {
        if (callID == null) {
            throw new IllegalArgumentException("Voice call ID must be specified.");
        }

        if (legId == null) {
            throw new IllegalArgumentException("Leg ID must be specified.");
        }

        if (recordingId == null) {
            throw new IllegalArgumentException("Recording ID must be specified.");
        }

        String url = String.format(
                "%s%s/%s%s/%s%s/%s%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                callID,
                LEGSPATH,
                legId,
                RECORDINGPATH,
                recordingId,
                TRANSCRIPTIONPATH);

        return messageBirdService.requestList(url, new PagedPaging(page, pageSize), TranscriptionResponse.class);
    }

    /**
     * Downloads the transcription in .txt format by using callId, legId, recordId and transcriptionId and stores to basePath. basePath is not mandatory to set.
     * If basePath is not set, default download will be the /Download folder in user group.
     *
     * @param callID Voice call ID
     * @param legId Leg ID
     * @param recordingId Recording ID
     * @param transcriptionId Transcription ID
     * @param basePath store location. It should be directory. Property is Optional if $HOME is accessible
     * @return the path that file is stored
     * @throws NotFoundException if the recording does not found
     * @throws GeneralException general exception
     * @throws UnauthorizedException if client is unauthorized
     */
    public String downloadTranscription(String callID, String legId, String recordingId, String transcriptionId, String basePath)
            throws UnauthorizedException, GeneralException, NotFoundException {
        if (callID == null) {
            throw new IllegalArgumentException("Voice call ID must be specified.");
        }

        if (legId == null) {
            throw new IllegalArgumentException("Leg ID must be specified.");
        }

        if (recordingId == null) {
            throw new IllegalArgumentException("Recording ID must be specified.");
        }

        if (transcriptionId == null) {
            throw new IllegalArgumentException("Transcription ID must be specified.");
        }
        String fileName = String.format("%s%s", transcriptionId, TRANSCRIPTION_DOWNLOAD_FORMAT);
        String url = String.format(
                "%s%s/%s%s/%s%s/%s%s/%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                callID,
                LEGSPATH,
                legId,
                RECORDINGPATH,
                recordingId,
                TRANSCRIPTIONPATH,
                fileName);

        return messageBirdService.getBinaryData(url, basePath, fileName);
    }

    /**
     * Function to create a webhook
     *
     * @param webhook webhook to create
     * @return WebhookResponseData created webhook
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public WebhookResponseData createWebhook(Webhook webhook) throws UnauthorizedException, GeneralException {
        if (webhook.getUrl() == null) {
            throw new IllegalArgumentException("URL of webhook must be specified.");
        }

        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, WEBHOOKS);
        return messageBirdService.sendPayLoad(url, webhook, WebhookResponseData.class);
    }

    /**
     * Function to update a webhook
     *
     * @param webhook webhook fields to update
     * @return WebhookResponseData updated webhook
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public WebhookResponseData updateWebhook(String id, Webhook webhook) throws UnauthorizedException, GeneralException {
        if (id == null) {
            throw new IllegalArgumentException("Id of webhook must be specified.");
        }

        String url = String.format("%s%s/%s", VOICE_CALLS_BASE_URL, WEBHOOKS, id);
        return messageBirdService.sendPayLoad("PUT", url, webhook, WebhookResponseData.class);
    }

    /**
     * Function to view a webhook
     *
     * @param id id of a webhook
     * @return WebhookResponseData
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public WebhookResponseData viewWebhook(String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Id of webhook must be specified.");
        }

        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, WEBHOOKS);
        return messageBirdService.requestByID(url, id, WebhookResponseData.class);
    }

    /**
     * Function to list webhooks
     *
     * @param offset offset for result list
     * @param limit  limit for result list
     * @return WebhookList
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public WebhookList listWebhooks(final Integer offset, final Integer limit) throws UnauthorizedException, GeneralException {
        verifyOffsetAndLimit(offset, limit);

        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, WEBHOOKS);
        return messageBirdService.requestList(url, offset, limit, WebhookList.class);
    }

    /**
     * Function to delete a webhook
     *
     * @param id A unique random ID which is created on the MessageBird platform
     * @throws NotFoundException     if id is not found
     * @throws GeneralException      general exception
     * @throws UnauthorizedException if client is unauthorized
     */
    public void deleteWebhook(String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Webhook ID must be specified.");
        }

        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, WEBHOOKS);
        messageBirdService.deleteByID(url, id);
    }

    private void verifyOffsetAndLimit(Integer offset, Integer limit) {
        if (offset != null && offset < 0) {
            throw new IllegalArgumentException("Offset must be > 0");
        }
        if (limit != null && limit < 0) {
            throw new IllegalArgumentException("Limit must be > 0");
        }
    }

    /**
     * Checks whether a particular country code is a recognized ISO Country.
     *
     * @param countryCode The country code in which the Number should be purchased.
     * @throws IllegalArgumentException for invalid country code
     */
    private void countryCodeIsValid(String countryCode) throws IllegalArgumentException {
        final boolean isValid = Arrays.asList(Locale.getISOCountries()).contains(countryCode);
        if (!isValid) {
            throw new IllegalArgumentException("Invalid Country Code Provided.");
        }
    }

    /**
     * Lists Numbers that are available to purchase in a particular country code, without any filters.
     *
     * @param countryCode The country code in which the Number should be purchased.
     * @throws GeneralException             general exception
     * @throws UnauthorizedException        if client is unauthorized
     * @throws NotFoundException            if the resource is missing
     * @throws IllegalArgumentException     if the country code provided is invalid
     */
    public PhoneNumbersResponse listNumbersForPurchase(String countryCode) throws GeneralException, UnauthorizedException, NotFoundException, IllegalArgumentException {
        countryCodeIsValid(countryCode);
        final String url = String.format("%s/available-phone-numbers", NUMBERS_CALLS_BASE_URL);
        return messageBirdService.requestByID(url, countryCode, PhoneNumbersResponse.class);
    }

    /**
     * Lists Numbers that are available to purchase in a particular country code, according to specified search criteria.
     *
     * @param countryCode   The country code in which the Number should be purchased.
     * @param params        Parameters to filter the resulting phone numbers returned.
     * @throws GeneralException             general exception
     * @throws UnauthorizedException        if client is unauthorized
     * @throws NotFoundException            if the resource is missing
     * @throws IllegalArgumentException     if the country code provided is invalid
     */
    public PhoneNumbersResponse listNumbersForPurchase(String countryCode, PhoneNumbersLookup params) throws GeneralException, UnauthorizedException, NotFoundException, IllegalArgumentException {
        countryCodeIsValid(countryCode);
        final String url = String.format("%s/available-phone-numbers", NUMBERS_CALLS_BASE_URL);
        return messageBirdService.requestByID(url, countryCode, params.toHashMap(), PhoneNumbersResponse.class);
    }

    /**
     * Purchases a phone number. To be used in conjunction with listNumbersForPurchase to identify available numbers.
     *
     * @param number        The number to purchase.
     * @param countryCode   The country code in which the Number should be purchased.
     * @throws GeneralException             general exception
     * @throws UnauthorizedException        if client is unauthorized
     * @throws IllegalArgumentException     if the country code provided is invalid
     */
    public PurchasedNumberCreatedResponse purchaseNumber(String number, String countryCode, int billingIntervalMonths) throws UnauthorizedException, GeneralException, IllegalArgumentException {
        countryCodeIsValid(countryCode);
        final String url = String.format("%s/phone-numbers", NUMBERS_CALLS_BASE_URL);
        final Map<String, Object> payload = new LinkedHashMap<String, Object>();
        payload.put("number", number);
        payload.put("countryCode", countryCode);
        if (!Arrays.asList(1, 3, 6, 9).contains(billingIntervalMonths)) {
            throw new IllegalArgumentException("Billing Interval Must Be Either 1, 3, 6, or 9.");
        }
        payload.put("billingIntervalMonths", billingIntervalMonths);

        return messageBirdService.sendPayLoad(url, payload, PurchasedNumberCreatedResponse.class);
    }

    /**
     * Lists Numbers that were purchased using the account credentials that the client was initialized with.
     *
     * @param filter Filters the list of purchased numbers according to search criteria.
     * @throws UnauthorizedException        if client is unauthorized
     * @throws GeneralException             general exception
     * @throws NotFoundException            if the resource is missing
     */
    public PurchasedNumbersResponse listPurchasedNumbers(PurchasedNumbersFilter filter) throws UnauthorizedException, GeneralException, NotFoundException {
        final String url = String.format("%s/phone-numbers", NUMBERS_CALLS_BASE_URL);
        return messageBirdService.requestByID(url, null, filter.toHashMap(), PurchasedNumbersResponse.class);
    }

    /**
     * Returns a Number that has already been purchased on the initialized account.
     *
     * @param number The number whose data should be returned.
     * @throws UnauthorizedException        if client is unauthorized
     * @throws GeneralException             general exception
     * @throws NotFoundException            if the Number is missing
     */
    public PurchasedNumber viewPurchasedNumber(String number) throws UnauthorizedException, GeneralException, NotFoundException {
        final String url = String.format("%s/phone-numbers", NUMBERS_CALLS_BASE_URL);
        return messageBirdService.requestByID(url, number, PurchasedNumber.class);
    }

    /**
     * Updates tags on a particular existing Number. Any number of parameters after the number can be given to apply multiple tags.
     *
     * @param number    The number to update.
     * @param tags      A tag to apply to the number.
     * @throws UnauthorizedException        if client is unauthorized
     * @throws GeneralException             general exception
     */
    public PurchasedNumber updateNumber(String number, String... tags) throws UnauthorizedException, GeneralException {
        final String url = String.format("%s/phone-numbers/%s", NUMBERS_CALLS_BASE_URL, number);
        final Map<String, List<String>> payload = new HashMap<String, List<String>>();
        payload.put("tags", Arrays.asList(tags));
        return messageBirdService.sendPayLoad("PATCH", url, payload, PurchasedNumber.class);
    }

    /**
     * Cancels a particular number.
     *
     * @param number The number to cancel.
     * @throws GeneralException             general exception
     * @throws UnauthorizedException        if client is unauthorized
     * @throws NotFoundException            if the resource is missing
     */
    public void cancelNumber(String number) throws UnauthorizedException, GeneralException, NotFoundException {
        final String url = String.format("%s/phone-numbers", NUMBERS_CALLS_BASE_URL);
        messageBirdService.deleteByID(url, number);
    }

    /**
     * Uploads a file and returns the assigned ID.
     *
     * @param binary the bytes of the file to upload.
     * @param contentType the content type of the file (e.g. "image/png").
     * @param filename optional filename to set in the upload request headers.
     * @return FileUploadResponse
     * @throws GeneralException      general exception
     * @throws UnauthorizedException if client is unauthorized
     * @see #downloadFile
     */
    public FileUploadResponse uploadFile(byte[] binary, String contentType, String filename) throws GeneralException, UnauthorizedException {
        if (binary == null) {
            throw new IllegalArgumentException("File binary must be specified.");
        }
        if (contentType == null) {
            throw new IllegalArgumentException("Content type must be specified.");
        }

        final String url = MESSAGING_BASE_URL + FILES_PATH;
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", contentType);
        if (filename != null) {
            headers.put("filename", filename);
        }
        return messageBirdService.sendPayLoad("POST", url, headers, binary, FileUploadResponse.class);
    }

    /**
     * Downloads a file and stores it with the provided filename in the basePath directory. The
     * basePath may be null. If basePath is null, the default download directory will be the
     * /Download folder in the user home directory. The filename may be null. If filename is null,
     * the provided id will be used as the filename instead.
     *
     * @param id       the ID of the file, provided when the file was uploaded
     * @param basePath store location. It should be a directory. Property is nullable if $HOME is accessible
     * @param filename the name of the file to download to.
     * @return the path where the downloaded file is stored
     * @throws NotFoundException     if the file does not exist
     * @throws GeneralException      general exception
     * @throws UnauthorizedException if client is unauthorized
     * @see #uploadFile
     */
    public String downloadFile(String id, String filename, String basePath) throws GeneralException, UnauthorizedException, NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("File ID must be specified.");
        }

        if (filename == null) {
            filename = id;
        }

        final String url = String.format("%s%s/%s", MESSAGING_BASE_URL, FILES_PATH, id);
        return messageBirdService.getBinaryData(url, basePath, filename);
    }

    /****************************************************************************************************/
    /** WhatsApp Templates                                                                             **/
    /****************************************************************************************************/

    /**
     * Create a WhatsApp message template through messagebird.
     *
     * @param template {@link Template} object to be created
     * @return {@link TemplateResponse} response object
     * @throws UnauthorizedException    if client is unauthorized
     * @throws GeneralException         general exception
     * @throws IllegalArgumentException invalid template format
     */
    public TemplateResponse createWhatsAppTemplate(final Template template)
        throws UnauthorizedException, GeneralException, IllegalArgumentException {
        template.validate();

        String url = String.format(
            "%s%s%s",
            INTEGRATIONS_BASE_URL_V2,
            INTEGRATIONS_WHATSAPP_PATH,
            TEMPLATES_PATH
        );
        return messageBirdService.sendPayLoad(url, template, TemplateResponse.class);
    }

    /**
     * Update a WhatsApp message template through MessageBird.
     *
     * @param template {@link Template} object to be created
     * @param templateName A name as returned by getWhatsAppTemplateBy in the name variable
     * @param language A language code as returned by getWhatsAppTemplateBy in the language variable
     * @return {@link TemplateResponse} response object
     * @throws UnauthorizedException    if client is unauthorized
     * @throws GeneralException         general exception
     * @throws IllegalArgumentException invalid template format
     */
    public TemplateResponse updateWhatsAppTemplate(final Template template, final String templateName, final String language)
            throws UnauthorizedException, GeneralException, IllegalArgumentException {
        template.validate();

        String url = String.format(
                "%s%s%s/%s/%s",
                INTEGRATIONS_BASE_URL_V2,
                INTEGRATIONS_WHATSAPP_PATH,
                TEMPLATES_PATH,
                templateName,
                language);

        return messageBirdService.sendPayLoad("PUT",url, template, TemplateResponse.class);
    }
    /**
     * Gets a WhatsAppTemplate listing with specified pagination options.
     *
     * @param offset Number of objects to skip.
     * @param limit  Number of objects to take.
     * @return List of templates.
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public TemplateList listWhatsAppTemplates(final int offset, final int limit)
        throws UnauthorizedException, GeneralException {
        String url = String.format(
            "%s%s%s",
            INTEGRATIONS_BASE_URL_V3,
            INTEGRATIONS_WHATSAPP_PATH,
            TEMPLATES_PATH
        );
        return messageBirdService.requestList(url, offset, limit, TemplateList.class);
    }

    /**
     * Gets a WhatsAppTemplate listing with specified pagination options and a wabaID or channelID filter.
     *
     * @param offset    Number of objects to skip.
     * @param limit     Number of objects to take.
     * @param wabaID    The WABA ID to filter templates by.
     * @param channelID A channel ID filter to return only templates that can be sent via that channel.
     * @return List of templates.
     * @throws UnauthorizedException    if client is unauthorized
     * @throws GeneralException         general exception
     * @throws IllegalArgumentException if the provided arguments are not valid
     */
    public TemplateList listWhatsAppTemplates(final int offset, final int limit, final String wabaID, final String channelID)
            throws UnauthorizedException, GeneralException, IllegalArgumentException {
        validateWABAIDAndChannelIDArguments(wabaID, channelID);

        Map<String, Object> map = new LinkedHashMap<>();
        if (wabaID != null) map.put("wabaId", wabaID);
        if (channelID != null) map.put("channelId", channelID);
        String url = String.format(
                "%s%s%s",
                INTEGRATIONS_BASE_URL_V3,
                INTEGRATIONS_WHATSAPP_PATH,
                TEMPLATES_PATH
        );
        return messageBirdService.requestList(url, map, offset, limit, TemplateList.class);
    }

    /**
     * Gets a template listing with default pagination options.
     *
     * @return List of whatsapp templates.
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public TemplateList listWhatsAppTemplates() throws UnauthorizedException, GeneralException {
        final int offset = 0;
        final int limit = 10;

        return listWhatsAppTemplates(offset, limit);
    }

    /**
     * Retrieves the template of an existing template name.
     *
     * @param templateName A name as returned by getWhatsAppTemplateBy in the name variable
     * @return {@code List<TemplateResponse>} template list
     * @throws UnauthorizedException    if client is unauthorized
     * @throws GeneralException         general exception
     * @throws NotFoundException        if template name is not found
     * @throws IllegalArgumentException if the provided arguments are not valid
     */
    public List<TemplateResponse> getWhatsAppTemplatesBy(final String templateName)
        throws GeneralException, UnauthorizedException, NotFoundException, IllegalArgumentException {
        if (templateName == null) {
            throw new IllegalArgumentException("Template name must be specified.");
        }

        String url = String.format(
            "%s%s%s",
            INTEGRATIONS_BASE_URL_V2,
            INTEGRATIONS_WHATSAPP_PATH,
            TEMPLATES_PATH
        );

        return messageBirdService.requestByIdAsList(url, templateName, TemplateResponse.class);
    }

    /**
     * Retrieves the template of an existing template name.
     *
     * @param templateName A name as returned by getWhatsAppTemplateBy in the name variable
     * @param wabaID An optional WABA ID to look for the template ID under.
     * @param channelID An optional channel ID to specify. If the template can be sent via the channel, it will return the template.
     *
     * @return {@code List<TemplateResponse>} template list
     * @throws UnauthorizedException    if client is unauthorized
     * @throws GeneralException         general exception
     * @throws NotFoundException        if template name is not found under the given WABA or cannot be sent under the supplied channel ID
     * @throws IllegalArgumentException if the provided arguments are not valid
     */
    public List<TemplateResponse> getWhatsAppTemplatesBy(final String templateName, final String wabaID, final String channelID)
            throws GeneralException, UnauthorizedException, NotFoundException, IllegalArgumentException {
        if (templateName == null) {
            throw new IllegalArgumentException("Template name must be specified.");
        }

        String id = String.format("%s%s", templateName, getWabaIDOrChannelIDQuery(wabaID, channelID));
        String url = String.format(
                "%s%s%s",
                INTEGRATIONS_BASE_URL_V2,
                INTEGRATIONS_WHATSAPP_PATH,
                TEMPLATES_PATH
        );

        return messageBirdService.requestByIdAsList(url, id, TemplateResponse.class);
    }

  /**
     * Retrieves the template of an existing template name and language under the first waba connected to the requesting user.
     *
     * @param templateName A name as returned by getWhatsAppTemplateBy in the name variable
     * @param language A language code as returned by getWhatsAppTemplateBy in the language variable
     *
     * @return {@code TemplateResponse} template
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     * @throws NotFoundException     if template name and language are not found under the first waba connected to the requesting user.
     * @throws IllegalArgumentException if the provided arguments are not valid
     */
    public TemplateResponse fetchWhatsAppTemplateBy(final String templateName, final String language)
        throws GeneralException, UnauthorizedException, NotFoundException, IllegalArgumentException {
        if (templateName == null || language == null) {
            throw new IllegalArgumentException("Template name and language must be specified.");
        }

        String url = String.format(
            "%s%s%s/%s/%s",
            INTEGRATIONS_BASE_URL_V2,
            INTEGRATIONS_WHATSAPP_PATH,
            TEMPLATES_PATH,
            templateName,
            language
        );
        return messageBirdService.request(url, TemplateResponse.class);
    }

    /**
     * Retrieves the template of an existing template name and language under a WABA or for a channel.
     *
     * @param templateName A name as returned by getWhatsAppTemplateBy in the name variable
     * @param language A language code as returned by getWhatsAppTemplateBy in the language variable
     * @param wabaID An optional WABA ID to look for the template ID under.
     * @param channelID An optional channel ID to specify. If the template can be sent via the channel, it will return the template.
     *
     * @return {@code TemplateResponse} template
     * @throws UnauthorizedException    if client is unauthorized
     * @throws GeneralException         general exception
     * @throws NotFoundException        if template name and language are not found under the given WABA or cannot be sent under the supplied channel ID.
     * @throws IllegalArgumentException if the provided arguments are not valid
     */
    public TemplateResponse fetchWhatsAppTemplateBy(final String templateName, final String language, final String wabaID, final String channelID)
            throws GeneralException, UnauthorizedException, NotFoundException, IllegalArgumentException {
        if (templateName == null || language == null) {
            throw new IllegalArgumentException("Template name and language must be specified.");
        }

        String url = String.format(
                "%s%s%s/%s/%s%s",
                INTEGRATIONS_BASE_URL_V2,
                INTEGRATIONS_WHATSAPP_PATH,
                TEMPLATES_PATH,
                templateName,
                language,
                getWabaIDOrChannelIDQuery(wabaID, channelID)
        );
        return messageBirdService.request(url, TemplateResponse.class);
    }

    /**
     * Validates the WABA ID and Channel ID argument pair.
     *
     * @param wabaID A WABA ID.
     * @param channelID A channel ID.
     * @throws IllegalArgumentException if the argument pair is invalid.
     */
    private void validateWABAIDAndChannelIDArguments(String wabaID, String channelID)
            throws IllegalArgumentException {
        if (wabaID == null && channelID == null) {
            throw new IllegalArgumentException("wabaID or channelID must be specified");
        }

        if (wabaID != null && channelID != null) {
            throw new IllegalArgumentException("only supply wabaID or channelID - not both");
        }
    }

    /**
     * Validates the WABA ID and Channel ID argument pair and returns a valid query parameter string.
     *
     * @param wabaID A WABA ID.
     * @param channelID A channel ID.
     * @throws IllegalArgumentException if the argument pair is invalid.
     */
    private String getWabaIDOrChannelIDQuery(String wabaID, String channelID)
            throws IllegalArgumentException {
        validateWABAIDAndChannelIDArguments(wabaID, channelID);

        String query = "";

        if (wabaID != null) {
            query = String.format("?wabaId=%s", wabaID);
        }
        if (channelID != null) {
            query = String.format("?channelId=%s", channelID);
        }

        return query;
    }

    /**
     * Delete templates of an existing template name.
     *
     * @param templateName A template name which is created on the MessageBird platform
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     * @throws NotFoundException     if template name is not found
     */
    public void deleteTemplatesBy(final String templateName)
        throws UnauthorizedException, GeneralException, NotFoundException {
        if (templateName == null) {
            throw new IllegalArgumentException("Template name must be specified.");
        }

        String url = String.format(
            "%s%s%s/%s",
            INTEGRATIONS_BASE_URL_V2,
            INTEGRATIONS_WHATSAPP_PATH,
            TEMPLATES_PATH,
            templateName
        );
        messageBirdService.delete(url, null);
    }

    public void unpauseTemplatesByTemplateName(final String templateName)
        throws UnauthorizedException, GeneralException {
        if (templateName == null) {
            throw new IllegalArgumentException("Template name must be specified.");
        }

        String url = String.format(
                "%s%s%s%s/%s",
                INTEGRATIONS_BASE_URL_V2,
                INTEGRATIONS_WHATSAPP_PATH,
                TEMPLATES_PATH,
                UNPAUSE_TEMAPLATE_PATH,
                templateName
        );
        messageBirdService.sendPayLoad("POST", url, "", null);
    }

    /**
     * Function to create a child account
     *
     * @param childAccountRequest of child account to create
     * @return ChildAccountResponse created
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public ChildAccountCreateResponse createChildAccount(final ChildAccountRequest childAccountRequest) throws UnauthorizedException, GeneralException {
        if (childAccountRequest.getName() == null || childAccountRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Name must be specified.");
        }

        String url = String.format("%s%s", PARTNER_ACCOUNTS_BASE_URL, "/child-accounts");
        return messageBirdService.sendPayLoad(url, childAccountRequest, ChildAccountCreateResponse.class);
    }

    /**
     * Function to update a child account
     *
     * @param id of child account to update
     * @return ChildAccountResponse created
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public ChildAccountResponse updateChildAccount(final String name, final String id) throws UnauthorizedException, GeneralException {
        if (name == null) {
            throw new IllegalArgumentException("Name must be specified.");
        }

        if (id == null) {
            throw new IllegalArgumentException("Child account id must be specified.");
        }
        final ChildAccountRequest childAccountRequest = new ChildAccountRequest();
        childAccountRequest.setName(name);
        final String url = String.format("%s/child-accounts/%s", PARTNER_ACCOUNTS_BASE_URL, id);
        return messageBirdService.sendPayLoad("PATCH", url, childAccountRequest, ChildAccountResponse.class);
    }

    /**
     * Function to get a child account
     *
     * @param id of child account to update
     * @return ChildAccountResponse created
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     *  @throws NotFoundException    if id is not found
     */
    public ChildAccountDetailedResponse getChildAccountById(final String id) throws UnauthorizedException, GeneralException, NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Child account id must be specified.");
        }
        return messageBirdService.requestByID(PARTNER_ACCOUNTS_BASE_URL + "/child-accounts", id, ChildAccountDetailedResponse.class);
    }

    /**
     * Function to get a child account
     *
     * @return ChildAccountResponse created
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public List<ChildAccountResponse> getChildAccounts(final Integer offset, final Integer limit) throws UnauthorizedException, GeneralException {
        verifyOffsetAndLimit(offset, limit);
        return messageBirdService.requestList(PARTNER_ACCOUNTS_BASE_URL + "/child-accounts", offset, limit, List.class);
    }

    /**
     * Function to delete a child account
     *
     * @param id of child account to delete
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     * @throws NotFoundException    if id is not found
     */
    public void deleteChildAccount(final String id) throws UnauthorizedException, GeneralException, NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Child account id must be specified.");
        }

        String url = String.format("%s/child-accounts", PARTNER_ACCOUNTS_BASE_URL);
        System.out.println("url: " + url);
        messageBirdService.deleteByID(url, id);
    }

    /**
     * Returns outbound pricing for the default SMS configuration for the authenticated account.
     *
     * @return outbound pricing for the default SMS configuration for the authenticated account
     *
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     * @throws NotFoundException     if pricing information could not be found
     *
     * @see <a href="https://developers.messagebird.com/quickstarts/pricingapi/list-outbound-sms-prices/">Pricing API</a>
     */
    public OutboundSmsPriceResponse getOutboundSmsPrices() throws GeneralException, UnauthorizedException, NotFoundException {
        return messageBirdService.request(OUTBOUND_SMS_PRICING_PATH, OutboundSmsPriceResponse.class);
    }

    /**
     * Returns outbound SMS pricing for a specific SMPP username.
     *
     * @param smppUsername the SMPP SystemID provided by MessageBird
     *
     * @return outbound SMS pricing for the given SMPP username
     *
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     * @throws NotFoundException     if pricing information could not be found for the given SMPP username
     *
     * @see <a href="https://developers.messagebird.com/quickstarts/pricingapi/list-outbound-sms-prices/">Pricing API</a>
     */
    public OutboundSmsPriceResponse getOutboundSmsPrices(final String smppUsername) throws GeneralException, UnauthorizedException, NotFoundException {
        if (smppUsername == null) {
            throw new IllegalArgumentException("SMPP username must be specified.");
        }

        final String url = String.format(OUTBOUND_SMS_PRICING_SMPP_PATH, smppUsername);
        return messageBirdService.request(url, OutboundSmsPriceResponse.class);
    }
} 
