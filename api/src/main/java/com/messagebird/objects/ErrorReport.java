package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * When MessageBird returns a 4xx, you will find a list of any error codes in your return dataset.
 * you will receive a list of errors from the API in such case.
 *
 * Created by rvt on 1/5/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorReport {
    private Integer code;
    private String description;
    private String parameter;
    private String message;

    public ErrorReport() {
    }

    public ErrorReport(Integer code, String description, String parameter, String message) {
        this.code = code;
        this.description = description;
        this.parameter = parameter;
        this.message = message;
    }

    @Override
    public String toString() {
        String str =  "ErrorReport{code=" + code;
        if (message != null && !message.isEmpty()) {
            str = str.concat(", message='" + message + "'");
        } else {
            str = str.concat(", description='" + description + "'");
            str = str.concat(", parameter='" + parameter + "'");
        }
        str = str.concat("}");
        return str;
    }

    /**
     * An integer that represents the error type.
     * @return
     */
    public Integer getCode() {
        return code;
    }

    /**
     * A human-readable description of the error. You can provide your users with this information to indicate what they can do about the error.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * parameter where the description and code talks about
     * @return
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * message not null for only voice API response
     * @return
     */
    public String getMessage() {
        return message;
    }
}
