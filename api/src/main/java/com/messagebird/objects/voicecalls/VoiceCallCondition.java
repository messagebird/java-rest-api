package com.messagebird.objects.voicecalls;

public class VoiceCallCondition {
    private String variable;
    private String operator;
    private String value;

    public VoiceCallCondition() {
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
