package com.messagebird.objects;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by faizan on 09/12/15.
 */
@Getter
@Setter
public class VerifyRequest implements Serializable {

    private String recipient;
    private String originator;
    private String reference;
    private VerifyType type;
    private DataCodingType datacoding = DataCodingType.plain;
    private String template;
    private Integer timeout;
    private Integer tokenLength;
    private Gender voice;
    private Language language;

    public VerifyRequest(String recipient) {
        this.recipient = recipient;
    }

}
