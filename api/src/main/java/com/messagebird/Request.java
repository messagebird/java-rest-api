package com.messagebird;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Arrays;

/**
 * Holds request data needed to calculate a signature hash for incoming
 * webhooks.
 */
public class Request {
    @Getter(value = AccessLevel.PACKAGE)
    private final String timestamp;

    private final String queryParameters;

    @Getter(value = AccessLevel.PACKAGE)
    private final byte[] data;

    private final static String QUERY_PARAMETERS_DELIMITER = "&";

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

    String getSortedQueryParameters() {
        String[] params = queryParameters.split(QUERY_PARAMETERS_DELIMITER);
        Arrays.sort(params);
        StringBuilder sortedParamsAccumulator = new StringBuilder();
        for (int i = 0, paramsLength = params.length; i < paramsLength; i++) {
            sortedParamsAccumulator.append(params[i]);
            if (i < paramsLength - 1) {
                sortedParamsAccumulator.append(QUERY_PARAMETERS_DELIMITER);
            }
        }
        return sortedParamsAccumulator.toString();
    }
}
