package com.messagebird.objects.voicecalls;

public enum VoiceCallStatus {

    queued("queued"),
    starting("starting"),
    ongoing("ongoing"),
    ended("ended");

    final String value;

    VoiceCallStatus(String type) {
        this.value = type;
    }

    public String getValue() {
        return value;
    }

}
