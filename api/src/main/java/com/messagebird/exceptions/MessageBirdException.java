package com.messagebird.exceptions;

import com.messagebird.objects.ErrorReport;

import java.util.List;

/**
 * Base class for MessageBird exceptions that can contain Error codes from the REST service
 *
 * Created by rvt on 1/6/15.
 */
public class MessageBirdException extends Exception {
    private List<ErrorReport> errors;

    public MessageBirdException(List<ErrorReport> errors) {
        this.errors = errors;
    }

    public MessageBirdException(String s, List<ErrorReport> errors) {
        super(s);
        this.errors = errors;
    }

    public MessageBirdException(String s, Throwable throwable, List<ErrorReport> errors) {
        super(s, throwable);
        this.errors = errors;
    }

    public MessageBirdException(Throwable throwable, List<ErrorReport> errors) {
        super(throwable);
        this.errors = errors;
    }

    public List<ErrorReport> getErrors() {
        return errors;
    }
}
