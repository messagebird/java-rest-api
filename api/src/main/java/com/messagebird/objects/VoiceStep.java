package com.messagebird.objects;

import java.io.Serializable;
import java.util.List;

public class VoiceStep implements Serializable {

    private static final long serialVersionUID = 7548590380013370980L;

    private String id;
    private String action;
    private VoiceStepOption options;
    private String onKeypressGoto;
    private String onKeypressVar;
    private List<Condition> conditions;

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

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return "VoiceStep{" +
                "id='" + id + '\'' +
                ", action='" + action + '\'' +
                ", options='" + options + '\'' +
                ", onKeypressGoto='" + onKeypressGoto + '\'' +
                ", onKeypressVar='" + onKeypressVar + '\'' +
                ", conditions=" + conditions +
                '}';
    }
}
