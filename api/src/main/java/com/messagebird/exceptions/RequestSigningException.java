package com.messagebird.exceptions;

/**
 * Thrown if an error occurs during request signing.
 *
 * @deprecated This class is being deprecated together with {@link com.messagebird.RequestSigner}
 */
@Deprecated
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
