package com.messagebird.exceptions;

import lombok.NoArgsConstructor;

/**
 * Thrown if an error occurs during request signing.
 */
@NoArgsConstructor
public class RequestSigningException extends RuntimeException {

    public RequestSigningException(String message) {
        super(message);
    }

    public RequestSigningException(Throwable cause) {
        super(cause);
    }
}
