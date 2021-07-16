package com.messagebird.exceptions;

/**
 * Thrown if an error occurs during request signing.
 */
public class RequestValidationException extends RuntimeException {

    public RequestValidationException() {
    }

    public RequestValidationException(String message) {
        super(message);
    }

    public RequestValidationException(Throwable cause) {
        super(cause);
    }

    public RequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
