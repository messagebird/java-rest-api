package com.messagebird;

/**
 * Represents a raw response received from the API.
 */
public class APIResponse {

    /**
     * Status indicates a successful request.
     */
    private static final int STATUS_OK = 200;

    private static final int STATUS_NO_CONTENT = 204;

    private static final int STATUS_RANGE_SUCCESS_START = 200;
    private static final int STATUS_RANGE_SUCCESS_END = 299;

    private final String body;
    private final int status;

    APIResponse(final String body, final int status) {
        this.body = body;
        this.status = status;
    }

    /**
     * Initializes an APIResponse object and sets the HTTP status code to 200.
     * @param body response body
     */
    APIResponse(final String body) {
        this(body, STATUS_OK);
    }

    /**
     * Determines whether the provided HTTP status code indicates success.
     *
     * @param status response status
     *
     * @return True if the status indicates success.
     */
     static boolean isSuccessStatus(final int status) {
        return status >= STATUS_RANGE_SUCCESS_START
                && status <= STATUS_RANGE_SUCCESS_END;
    }

    /**
     * Determines whether this request is successful based on its HTTP status
     * code.
     *
     * @return True if the request is successful.
     */
    public boolean isSuccess() {
        return isSuccessStatus(status);
    }

    /**
     * Determines whether this request has content, based on its HTTP status
     * code and otherwise by inspecting the response body.
     *
     * @return True if the request has content.
     */
    public boolean hasContent() {
        return status == STATUS_NO_CONTENT
                || body == null
                || body.isEmpty();
    }

    public String getBody() {
        return body;
    }

    public int getStatus() {
        return status;
    }
}
