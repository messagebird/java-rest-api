package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import com.messagebird.objects.conversations.*;
import com.messagebird.objects.voicecalls.*;
import com.messagebird.objects.voicecalls.VoiceCallLeg;
import com.messagebird.objects.voicecalls.VoiceCallLegResponse;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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

    /**
     * The Conversations API has a different URL scheme from the other
     * APIs/endpoints. By default, the service prefixes paths with that URL. We
     * can, however, override this behaviour by providing absolute URLs
     * ourselves.
     */
    private static final String CONVERSATIONS_BASE_URL = "https://conversations.messagebird.com/v1";
    static final String VOICE_CALLS_BASE_URL = "https://voice.messagebird.com";
    private static String[] supportedLanguages = {"de-DE", "en-AU", "en-UK", "en-US", "es-ES", "es-LA", "fr-FR", "it-IT", "nl-NL", "pt-BR"};

    private static final String BALANCEPATH = "/balance";
    private static final String CONTACTPATH = "/contacts";
    private static final String GROUPPATH = "/groups";
    private static final String HLRPATH = "/hlr";
    private static final String LOOKUPHLRPATH = "/lookup/%s/hlr";
    private static final String LOOKUPPATH = "/lookup";
    private static final String MESSAGESPATH = "/messages";
    private static final String VERIFYPATH = "/verify";
    private static final String VOICEMESSAGESPATH = "/voicemessages";
    private static final String CONVERSATION_PATH = "/conversations";
    private static final String CONVERSATION_MESSAGE_PATH = "/messages";
    private static final String CONVERSATION_WEBHOOK_PATH = "/webhooks";
    static final String VOICECALLSPATH = "/calls";
    static final String LEGSPATH = "/legs";
    static final String RECORDINGPATH = "/recordings";
    static final String TRANSCRIPTIONPATH = "/transcriptions";
    static final String WEBHOOKS = "/webhooks";
    private static final String VOICELEGS_SUFFIX_PATH = "/legs";

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
        return messageBirdService.sendPayLoad(VOICEMESSAGESPATH, message, VoiceMessageResponse.class);
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
        if (offset != null && offset < 0) {
            throw new IllegalArgumentException("Offset must be > 0");
        }
        if (limit != null && limit < 0) {
            throw new IllegalArgumentException("Limit must be > 0");
        }
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
    Verify getVerifyObject(String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty for verify");
        }
        return messageBirdService.requestByID(VERIFYPATH, id, Verify.class);
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
        String url = String.format("%s%s/%s", CONVERSATIONS_BASE_URL, CONVERSATION_PATH, id);
        return messageBirdService.sendPayLoad("PATCH", url, status, Conversation.class);
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
    public ConversationWebhook sendConversationWebhook(final ConversationWebhookRequest request)
            throws UnauthorizedException, GeneralException {
        String url = CONVERSATIONS_BASE_URL + CONVERSATION_WEBHOOK_PATH;
        return messageBirdService.sendPayLoad(url, request, ConversationWebhook.class);
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
    ConversationWebhookList listConversationWebhooks(final int offset, final int limit)
            throws UnauthorizedException, GeneralException {
        String url = CONVERSATIONS_BASE_URL + CONVERSATION_WEBHOOK_PATH;
        return messageBirdService.requestList(url, offset, limit, ConversationWebhookList.class);
    }

    /**
     * Gets a ConversationWebhook listing with default pagination options.
     *
     * @return List of webhooks.
     */
    public ConversationWebhookList listConversationWebHooks() throws UnauthorizedException, GeneralException {
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
     * @return TranscriptionResponseList
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public TranscriptionResponse viewTranscription(String callID, String legId, String recordingId, Integer page, Integer pageSize) throws UnauthorizedException, GeneralException {
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
                "%s%s/%s%s/%s%s/%s",
                VOICE_CALLS_BASE_URL,
                VOICECALLSPATH,
                callID,
                LEGSPATH,
                legId,
                RECORDINGPATH,
                recordingId);

        return messageBirdService.requestList(url, new PagedPaging(page, pageSize), TranscriptionResponse.class);
    }

    /**
     * Function to create web hook
     *
     * @param webhook title, url and token of webHook
     * @return WebHookResponseData
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public WebhookResponseData createWebHook(Webhook webhook) throws UnauthorizedException, GeneralException {
        if (webhook.getTitle() == null) {
            throw new IllegalArgumentException("Title of webhook must be specified.");
        }

        if (webhook.getUrl() == null) {
            throw new IllegalArgumentException("URL of webhook must be specified.");
        }

        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, WEBHOOKS);
        return messageBirdService.sendPayLoad(url, webhook, WebhookResponseData.class);
    }

    /**
     * Function to view webhook
     *
     * @param id webHook id
     * @return WebHookResponseData
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException      general exception
     */
    public WebhookResponseData viewWebHook(String id) throws NotFoundException, GeneralException, UnauthorizedException {
        if (id == null) {
            throw new IllegalArgumentException("Id of webHook must be specified.");
        }

        String url = String.format("%s%s", VOICE_CALLS_BASE_URL, WEBHOOKS);
        return messageBirdService.requestByID(url, id, WebhookResponseData.class);
    }
}