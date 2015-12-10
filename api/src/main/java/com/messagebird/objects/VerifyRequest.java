package com.messagebird.objects;

import java.io.Serializable;

/**
 * Created by faizan on 09/12/15.
 */
public class VerifyRequest implements Serializable {

    private String recipient;
    private String originator;
    private String reference;
    private MsgType type;
    private String template;
    private Integer timeout;
    private Integer tokenLength;
    private Gender voice;
    private Language language;


    public VerifyRequest(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getTokenLength() {
        return tokenLength;
    }

    public void setTokenLength(Integer tokenLength) {
        this.tokenLength = tokenLength;
    }

    public Gender getVoice() {
        return voice;
    }

    public void setVoice(Gender voice) {
        this.voice = voice;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
