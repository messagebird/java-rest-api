package com.messagebird.objects;

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

    private String originator;
    private String body;
    private String recipients;
    private String reference;
    private String language;
    private VoiceType voice;
    private Integer repeat;
    private IfMachineType ifMachine;
    private int machineTimeout;
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
     * The originator (sender) of the message
     *
     * @return String
     */
    public String getOriginator() {
        return originator;
    }

    /**
     * Set the originator (sender) of the message
     *
     * @return void
     */
    public void setOriginator(String originator) {
        this.originator = originator;
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

    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * The language in which the message needs to be read to the recipient. Possible values are: nl-nl, de-de, en-gb, en-us, fr-fr.
     *
     * @return seleted language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * The language in which the message needs to be read to the recipient. Possible values are: nl-nl, de-de, en-gb, en-us, fr-fr.
     * Default: en-gb (set by messagebird server if not send)
     *
     * @param language Possible values are: nl-nl, de-de, en-gb, en-us, fr-fr.
     */
    public void setLanguage(String language) {
        this.language = language;
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
     * The voice in which the messages needs to be read to the recipient. Possible values are: male, female.
     * Default: female (set by messagebird server if not send)
     *
     * @param voice Possible values are: male, female.
     */
    public void setVoice(VoiceType voice) {
        this.voice = voice;
    }

    /**
     * How many times needs the message to be repeated?
     *
     * @return Number of repetitions
     */
    public Integer getRepeat() {
        return repeat;
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

    /**
     * What to do when a machine picks up the phone? Possible values are:
     * - continue do not check, just play the message
     * - delay if a machine answers, wait until the machine stops talking
     * - hangup hangup when a machine answers
     *
     * @return what to do if a machine picks up
     */
    public IfMachineType getIfMachine() {
        return ifMachine;
    }

    /**
     * What to do when a machine picks up the phone? Possible values are:
     * - continue do not check, just play the message
     * - delay if a machine answers, wait until the machine stops talking
     * - hangup hangup when a machine answers
     * Default: continue (set by messagebird server if not send)
     *
     * @param ifMachine
     */
    public void setIfMachine(IfMachineType ifMachine) {
        this.ifMachine = ifMachine;
    }

    /**
     * The time (in milliseconds) to analyze if a machine has picked up the phone.
     * Used in combination with the delay and hangup values of the ifMachine attribute.
     * Minimum: 400, maximum: 10000. Default: 7000
     * @return value of machine timeout
     */
    public int getMachineTimeout() { return machineTimeout; }

    /**
     * The time (in milliseconds) to analyze if a machine has picked up the phone.
     * Used in combination with the delay and hangup values of the ifMachine attribute.
     * Minimum: 400, maximum: 10000. Default: 7000
     * @param machineTimeout value of machine timeout
     */
    public void setMachineTimeout(int machineTimeout) { this.machineTimeout = machineTimeout; }

    @Override
    public Date getScheduledDatetime() {
        return scheduledDatetime;
    }

    /**
     * The scheduled date and time of the message. Allows to specify when MessageBird server send's the mesage.
     *
     * @param scheduledDatetime Date
     */
    public void setScheduledDatetime(Date scheduledDatetime) {
        this.scheduledDatetime = scheduledDatetime;
    }
}
