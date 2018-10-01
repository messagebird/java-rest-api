package com.messagebird.objects;

import java.io.Serializable;

/**
 * Created by faizan on 09/12/15.
 */
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

    public VerifyType getType() {
        return type;
    }

    public void setType(VerifyType type) {
        this.type = type;
    }

    /**
     * The datacoding used by the template.
     *
     * @return returns plain or unicode
     */
    public DataCodingType getDatacoding() {
        return datacoding;
    }

    /**
     * The datacoding used by the template.
     *
     * @param datacoding
     */
    public void setDatacoding(DataCodingType datacoding) {
        this.datacoding = datacoding;
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
