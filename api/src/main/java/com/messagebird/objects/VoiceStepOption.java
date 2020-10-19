package com.messagebird.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
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
    private String keys;
    private int duration;
    private int interval;
}
