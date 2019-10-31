package com.messagebird.objects;

import java.io.Serializable;

public class VoiceStepOption implements Serializable {

    private static final long serialVersionUID = -3049137903264066344L;

    private String destination;
    private String payload;
    private String language;
    private String voice;
    private int repeat;
    private String media;
    private int length;
    private int maxLength;
    private int timeout;
    private String finishOnKey;
    private boolean transcribe;
    private String transcribeLanguage;
    private String record;
    private String url;
    private String ifMachine;
    private int machineTimeout;
    private String onFinish;
    private boolean mask;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getFinishOnKey() {
        return finishOnKey;
    }

    public void setFinishOnKey(String finishOnKey) {
        this.finishOnKey = finishOnKey;
    }

    public boolean isTranscribe() {
        return transcribe;
    }

    public void setTranscribe(boolean transcribe) {
        this.transcribe = transcribe;
    }

    public String getTranscribeLanguage() {
        return transcribeLanguage;
    }

    public void setTranscribeLanguage(String transcribeLanguage) {
        this.transcribeLanguage = transcribeLanguage;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIfMachine() {
        return ifMachine;
    }

    public void setIfMachine(String ifMachine) {
        this.ifMachine = ifMachine;
    }

    public int getMachineTimeout() {
        return machineTimeout;
    }

    public void setMachineTimeout(int machineTimeout) {
        this.machineTimeout = machineTimeout;
    }

    public String getOnFinish() {
        return onFinish;
    }

    public void setOnFinish(String onFinish) {
        this.onFinish = onFinish;
    }

    public boolean isMask() {
        return mask;
    }

    public void setMask(boolean mask) {
        this.mask = mask;
    }

    @Override
    public String toString() {
        return "VoiceStepOption{" +
                "destination='" + destination + '\'' +
                ", payload='" + payload + '\'' +
                ", language='" + language + '\'' +
                ", voice='" + voice + '\'' +
                ", repeat='" + repeat + '\'' +
                ", media='" + media + '\'' +
                ", length=" + length +
                ", maxLength=" + maxLength +
                ", timeout=" + timeout +
                ", finishOnKey='" + finishOnKey + '\'' +
                ", transcribe=" + transcribe +
                ", transcribeLanguage='" + transcribeLanguage + '\'' +
                ", record='" + record + '\'' +
                ", url='" + url + '\'' +
                ", ifMachine='" + ifMachine + '\'' +
                ", machineTimeout=" + machineTimeout +
                ", onFinish='" + onFinish + '\'' +
                ", mask=" + mask +
                '}';
    }
}
