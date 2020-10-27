package com.messagebird.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Holder for a Voice message response
 * <p>
 * Created by rvt on 1/7/15.
 */
@ToString
@NoArgsConstructor
public class VoiceMessageResponse implements MessageResponseBase, Serializable {

    private static final long serialVersionUID = -8605510461438669940L;

    private String id;
    private String href;
    private String body;
    private String reference;
    private MessageResponse.Recipients recipients;

    @Getter
    private String originator;

    @Getter
    private String language;

    @Getter
    private VoiceType voice;

    @Getter
    private Integer repeat;

    @Getter
    private IfMachineType ifMachine;

    @Getter
    private int machineTimeout;

    @Getter
    private Date scheduledDatetime;

    @Getter
    private Date createdDatetime;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getHref() {
        return href;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public MessageResponse.Recipients getRecipients() {
        return recipients;
    }
}
