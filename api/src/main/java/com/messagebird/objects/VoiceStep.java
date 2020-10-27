package com.messagebird.objects;

import com.messagebird.objects.voicecalls.VoiceCallCondition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class VoiceStep implements Serializable {
    @Getter
    private static final long serialVersionUID = 7548590380013370980L;

    private String id;
    private String action;
    private VoiceStepOption options;

    private VoiceCallCondition[] conditions;

    private String onKeypressGoto;
    private String onKeypressVar;
}
