package com.messagebird.objects.voicecalls;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum VoiceCallStatus {

    queued("queued"),
    starting("starting"),
    ongoing("ongoing"),
    ended("ended"),
    failed("failed"),
    busy("busy"),
    no_answer("no_answer");

    @Getter
    final String value;
}
