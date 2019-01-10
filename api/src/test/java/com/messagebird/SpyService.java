package com.messagebird;

import com.messagebird.exceptions.GeneralException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * Builder for effortlessly constructing spy services of MessageBirdServiceImpl.
 * <p>
 * The following example configures a mock to return an OK response with a
 * response of your choice whenever doRequest() is called:
 *
 * <pre>
 * MessageBirdService messageBirdService = SpyService
 *      .expects("GET", "conversatons/convid")
 *      .withConversationsAPIBaseURL()
 *      .andReturns(new ApiResponse("YOUR_RESPONSE_BODY"));
 * </pre>
 *
 * @param <P> Type of the payload being (optionally) returned.
 */
class SpyService<P> {

    private static final String CONVERSATIONS_API_BASE_URL = "https://conversations.messagebird.com/v1";
    private static final String REST_API_BASE_URL = "https://rest.messagebird.com";
    private static final String VOICE_CALLS_BASE_URL = "https://voice.messagebird.com";

    private String method;
    private String url;
    private P payload;

    private String baseURL;

    SpyService() {
        //
    }

    /**
     * Gets the access key for the MessageBirdService. Can be overridden, but
     * if a use case requires this, the mock is likely not used properly.
     * <p>
     * It is strongly advisable to NOT make this a valid access key for several
     * reasons, but also because Mockito uses loose mocks. This means that if a
     * mocked method is invoked without any matching expectations (for example,
     * with the wrong parameters), it does not throw exceptions. It instead
     * calls the real implementation. Leaving the access key blank ensures an
     * exception is thrown ("not authorized"), causing the test to fail.
     *
     * @return Access key for MessageBirdService.
     */
    protected String getAccessKey() {
        return "";
    }

    /**
     * Sets up a spy and configures its expectations for doRequest().
     *
     * @param method  Method that doRequest() expects.
     * @param url     URL that doRequest() expects.
     * @param payload Payload that doRequest() expects.
     * @param <P>     Type of the payload.
     * @return Intermediate SpyService that can be finalized through
     * andReturns().
     */
    static <P> SpyService expects(final String method, final String url, final P payload) {
        SpyService service = new SpyService<P>();
        service.method = method;
        service.url = url;
        service.payload = payload;

        return service;
    }

    /**
     * Sets up a spy and configures its expectations for doRequest(). This sets
     * the expected payload to null - useful for requests without bodies, like
     * GETs and DELETEs. To provide an expected payload, use the overload.
     *
     * @param method Method that doRequest() expects.
     * @param url    URL that doRequest() expects.
     * @return Intermediate SpyService that can be finalized through
     * andReturns().
     */
    static SpyService expects(final String method, final String url) {
        return SpyService.expects(method, url, null);
    }

    /**
     * Sets a base URL to prefix the URL provided to expects() with when
     * building the spy.
     *
     * @param baseURL String to prefix the URL with when building the spy.
     * @return Intermediate SpyService that can be finalized through
     * andReturns().
     */
    SpyService withBaseURL(final String baseURL) {
        this.baseURL = baseURL;

        return this;
    }

    /**
     * Sets the base URL to match the Conversations API's.
     *
     * @return Intermediate SpyService that can be finalized through
     * andReturns().
     */
    SpyService withConversationsAPIBaseURL() {
        return withBaseURL(CONVERSATIONS_API_BASE_URL);
    }

    /**
     * Prefixes all URLs provided to expects() with the REST API's base URL
     * when building the spy.
     *
     * @return Intermediate SpyService that can be finalized through
     * andReturns().
     */
    SpyService withRestAPIBaseURL() {
        return withBaseURL(REST_API_BASE_URL);
    }

    /**
     * Sets the base URL to match the Voice Call API's.
     *
     * @return Intermediate SpyService that can be finalized through
     * andReturns().
     */
    SpyService withVoiceCallAPIBaseURL() {
        return withBaseURL(VOICE_CALLS_BASE_URL);
    }


    /**
     * Finalizes the SpyService by configuring its return value for
     * doRequest() and builds the spy.
     *
     * @param apiResponse APIResponse to return from the spy when doRequest()
     *                    is invoked with the configured expectation.
     * @return MessageBirdServiceImpl with a spy on doRequest().
     * @throws GeneralException
     */
    MessageBirdService andReturns(final APIResponse apiResponse) throws GeneralException {
        if (baseURL != null && !baseURL.isEmpty()) {
            url = String.format("%s/%s", baseURL, url);
        }

        MessageBirdServiceImpl messageBirdService = spy(new MessageBirdServiceImpl(getAccessKey()));
        doReturn(apiResponse).when(messageBirdService).doRequest(method, url, payload);

        return messageBirdService;
    }
}
