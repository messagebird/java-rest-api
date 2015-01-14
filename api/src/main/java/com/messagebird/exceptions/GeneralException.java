package com.messagebird.exceptions;

import com.messagebird.objects.ErrorReport;

import java.util.List;

/**
 * General exceptions send by the system. It might or might not contain a list of error codes
 *
 * Created by rvt on 1/5/15.
 */
public class GeneralException extends MessageBirdException {
    private Integer responseCode=null;
    public GeneralException(List<ErrorReport> errors) {
        super(errors);
    }

    public GeneralException(String s, List<ErrorReport> errors) {
        super(s, errors);
    }
    public GeneralException(String s) {
        super(s, null);
    }

    public GeneralException(String s, Throwable throwable, List<ErrorReport> errors) {
        super(s, throwable, errors);
    }

    public GeneralException(Throwable throwable, List<ErrorReport> errors) {
        super(throwable, errors);
    }
    public GeneralException(Throwable throwable) {
        super(throwable, null);
    }

    public GeneralException(String s, Integer responseCode) {
        this(s);
        this.responseCode = responseCode;
    }

    public GeneralException(String s, int responseCode, List<ErrorReport> errorReport) {
        super(s, errorReport);
        this.responseCode = responseCode;
    }

    public Integer getResponseCode() {
        return responseCode;
    }
}
