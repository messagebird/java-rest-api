package com.messagebird.objects;

import java.io.Serializable;

public class Condition implements Serializable {

    private static final long serialVersionUID = 137098075485857204L;

    private String variable;
    private String operator;
    private String value;

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

    @Override
    public String toString() {
        return "Condition{" +
                "variable='" + variable + '\'' +
                ", operator='" + operator + '\'' +
                ", value='" + value +
                '}';
    }
}
