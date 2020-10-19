package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * When MessageBird returns a 4xx, you will find a list of any error codes in your return dataset.
 * you will receive a list of errors from the API in such case.
 *
 * Created by rvt on 1/5/15.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorReport {
    private Integer code;
    private String description;
    private String parameter;
    private String message;
}
