package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VoiceLegDirection {
    Incoming, Outgoing;

    @JsonCreator
    public static VoiceLegDirection forValue(String value) {
        switch (value) {
            case "incoming": return Incoming;
            case "outgoing": return Outgoing;
            default: throw new IllegalArgumentException("Unknown leg direction: " + value);
        }
    }
}
