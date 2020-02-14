package com.messagebird.objects.voicecalls;

public enum VoiceCallStatus {

    queued("queued"),
    starting("starting"),
    ongoing("ongoing"),
    ended("ended"),
    failed("failed"),
    busy("busy"),
    no_answer("no_answer");

    final String value;

    VoiceCallStatus(String type) {
        this.value = type;
    }

    public String getValue() {
        return value;
    }

}
