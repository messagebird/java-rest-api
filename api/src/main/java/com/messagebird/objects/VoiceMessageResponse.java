package com.messagebird.objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Holder for a Voice message response
 *
 * Created by rvt on 1/7/15.
 */
public class VoiceMessageResponse implements MessageResponseBase, Serializable {

    private static final long serialVersionUID = -8605510461438669940L;

    private String id;
    private String href;
    private String originator;
    private String body;
    private String reference;
    private String language;
    private VoiceType voice;
    private Integer repeat;
    private IfMachineType ifMachine;
    private int machineTimeout;
    private Date scheduledDatetime;
    private Date createdDatetime;
    private MessageResponse.Recipients recipients;

    public VoiceMessageResponse() {
    }

    @Override
    public String toString() {
        return "VoiceMessageResponse{" +
                "id='" + id + '\'' +
                ", href='" + href + '\'' +
                ", originator='" + originator +'\'' +
                ", body='" + body + '\'' +
                ", reference='" + reference + '\'' +
                ", language='" + language + '\'' +
                ", voice=" + voice +
                ", repeat=" + repeat +
                ", ifMachine=" + ifMachine +
                ", machineTimeout=" + machineTimeout +
                ", scheduledDatetime=" + scheduledDatetime +
                ", createdDatetime=" + createdDatetime +
                ", recipients=" + recipients +
                '}';
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getHref() {
        return href;
    }

    /**
     * The originator (sender) of the message
     *
     * @return String
     */
    public String getOriginator() {
        return originator;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getReference() {
        return reference;
    }

    /**
     * The language in which the message needs to be read to the recipient. Possible values are: nl-nl, de-de, en-gb, en-us, fr-fr.
     *
     * @return
     */
    public String getLanguage() {
        return language;
    }

    /**
     * The voice in which the messages needs to be read to the recipient. Possible values are: male, female.
     *
     * @return
     */
    public VoiceType getVoice() {
        return voice;
    }

    /**
     * How many times needs the message to be repeated?
     *
     * @return
     */
    public Integer getRepeat() {
        return repeat;
    }

    /**
     * What to do when a machine picks up the phone? Possible values are:
     * - continue do not check, just play the message
     * - delay if a machine answers, wait until the machine stops talking
     * - hangup hangup when a machine answers
     *
     * @return
     */
    public IfMachineType getIfMachine() {
        return ifMachine;
    }

    /**
     * The time (in milliseconds) to analyze if a machine has picked up the phone.
     * Used in combination with the delay and hangup values of the ifMachine attribute.
     * Minimum: 400, maximum: 10000. Default: 7000
     * @return value of machine timeout
     */
    public int getMachineTimeout() { return machineTimeout; }

    /**
     * The scheduled date and time of the message
     *
     * @return
     */
    public Date getScheduledDatetime() {
        return scheduledDatetime;
    }

    /**
     * The date and time of the creation of the message
     *
     * @return
     */
    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    @Override
    public MessageResponse.Recipients getRecipients() {
        return recipients;
    }
}
