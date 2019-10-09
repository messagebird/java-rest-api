package com.messagebird.objects;

import com.messagebird.objects.voicecalls.VoiceCallCondition;

import java.io.Serializable;
import java.util.Arrays;

public class VoiceStep implements Serializable {

    private static final long serialVersionUID = 7548590380013370980L;

    private String id;
    private String action;
    private VoiceStepOption options;

    private VoiceCallCondition[] conditions;

    private String onKeypressGoto;
    private String onKeypressVar;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOnKeypressGoto() {
        return onKeypressGoto;
    }

    public void setOnKeypressGoto(String onKeypressGoto) {
        this.onKeypressGoto = onKeypressGoto;
    }

    public String getOnKeypressVar() {
        return onKeypressVar;
    }

    public void setOnKeypressVar(String onKeypressVar) {
        this.onKeypressVar = onKeypressVar;
    }

    @Override
    public String toString() {
        return "VoiceStep{" +
                "id='" + id + '\'' +
                ", action='" + action + '\'' +
                ", options=" + options +
                ", conditions=" + Arrays.toString(conditions) +
                ", onKeypressGoto='" + onKeypressGoto + '\'' +
                ", onKeypressVar='" + onKeypressVar + '\'' +
                '}';
    }
}
