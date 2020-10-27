package com.messagebird.objects.voicecalls;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoiceCallCondition {
    private String variable;
    private String operator;
    private String value;
}
