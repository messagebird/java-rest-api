package com.messagebird.objects;

import com.messagebird.objects.voicecalls.VoiceCallCondition;

import java.io.Serializable;

public class VoiceStep implements Serializable {

    private static final long serialVersionUID = 7548590380013370980L;

    private String id;
    private String action;
    private VoiceStepOption options;

    private VoiceCallCondition[] conditions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public VoiceStepOption getOptions() {
        return options;
    }

    public void setOptions(VoiceStepOption options) {
        this.options = options;
    }

    public VoiceCallCondition[] getConditions() {
        return conditions;
    }

    public void setConditions(VoiceCallCondition[] conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return "VoiceStep{" +
                "id='" + id + '\'' +
                ", action='" + action + '\'' +
                ", options=" + options +
                '}';
    }
}
