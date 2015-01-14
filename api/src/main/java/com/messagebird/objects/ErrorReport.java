package com.messagebird.objects;

/**
 * When MessageBird returns a 4xx, you will find a list of any error codes in your return dataset.
 * you will receive a list of errors from the API in such case.
 *
 * Created by rvt on 1/5/15.
 */
public class ErrorReport {
    private Integer code;
    private String description;
    private String parameter;

    public ErrorReport() {
    }

    public ErrorReport(Integer code, String description, String parameter) {
        this.code = code;
        this.description = description;
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return "ErrorReport{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", parameter='" + parameter + '\'' +
                '}';
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

}
