package com.messagebird.exceptions;

/**
 * Thrown if an error occurs during request signing.
 */
public class RequestSigningException extends RuntimeException {

    public RequestSigningException() {
    }

    public RequestSigningException(String message) {
        super(message);
    }

    public RequestSigningException(Throwable cause) {
        super(cause);
    }
}
