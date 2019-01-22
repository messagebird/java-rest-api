package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VoiceLegStatus {
    Starting,
    Ringing,
    Ongoing,
    Busy,
    NoAnswer,
    Failed,
    Hangup;

    @JsonCreator
    public static VoiceLegStatus forValue(String value) {
        switch (value) {
            case "starting": return Starting;
            case "ringing": return Ringing;
            case "ongoing": return Ongoing;
            case "busy": return Busy;
            case "no_answer": return NoAnswer;
            case "failed": return Failed;
            case "hangup": return Hangup;
            default: throw new IllegalArgumentException("Unknown leg status: " + value);
        }
    }
}
