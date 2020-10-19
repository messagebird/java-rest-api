package com.messagebird.objects;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Class to hold's a Voice message that can be send
 * <p/>
 * Created by rvt on 1/7/15.
 */
public class VoiceMessage implements MessageBase, Serializable {

    private static final long serialVersionUID = 1553564355131214875L;

    @Getter
    @Setter
    private String originator;

    private String body;
    private String recipients;

    @Setter
    private String reference;

    @Getter
    @Setter
    private String language;

    @Getter
    @Setter
    private VoiceType voice;

    private Integer repeat;

    @Getter
    @Setter
    private IfMachineType ifMachine;

    @Getter
    @Setter
    private int machineTimeout;

    @Setter
    private Date scheduledDatetime;

    public VoiceMessage(String body, List<BigInteger> recipients) {
        this(body, Message.receipientsAsCommaSeperated(recipients));
    }

    public VoiceMessage(String body, String recipients) {
        if (recipients == null || recipients.trim().length() == 0) {
            throw new IllegalArgumentException("Recipients must be specified");
        }
        if (body == null || body.trim().length() == 0) {
            throw new IllegalArgumentException("Body must be specified");
        }
        this.recipients = recipients.trim();
        this.body = body.trim();
    }

    /**
     * The body of the SMS message.
     *
     * @return Message body
     */
    @Override
    public String getBody() {
        return body;
    }


    /**
     * The list of recipients msisdn's.
     *
     * @return String of comma separated recipients
     */
    @Override
    public String getRecipients() {
        return recipients;
    }

    @Override
    public String getReference() {
        return reference;
    }

    /**
     * How many times needs the message to be repeated?
     * Default: 1 (set by messagebird server if not send)
     *
     * @param repeat number of repetitions for the voice message
     */
    public void setRepeat(Integer repeat) {
        if (repeat < 1) {
            throw new IllegalArgumentException("Repeat cannot be < 1.");
        }
        this.repeat = repeat;
    }

    @Override
    public Date getScheduledDatetime() {
        return scheduledDatetime;
    }
}
