package com.messagebird;

/**
 * Holds request data needed to calculate a signature hash for incoming
 * webhooks.
 */
public class Request {

    private final String timestamp;
    private final String queryParameters;
    private final byte[] data;

    /**
     * Constructs a new request instance.
     *
     * @param timestamp Timestamp provided in the MessageBird-Request-Timestamp
     *                  header.
     * @param queryParameters Query parameters in abc=foo&def=ghi format.
     * @param data Raw body of this request.
     */
    public Request(String timestamp, String queryParameters, byte[] data) {
        if (timestamp == null || timestamp.isEmpty()) {
            throw new IllegalArgumentException("Timestamp can not be null or empty");
        }

        this.timestamp = timestamp;
        this.queryParameters = queryParameters;
        this.data = data;
    }

    String getTimestamp() {
        return timestamp;
    }

    String getQueryParameters() {
        return queryParameters;
    }

    byte[] getData() {
        return data;
    }
}
