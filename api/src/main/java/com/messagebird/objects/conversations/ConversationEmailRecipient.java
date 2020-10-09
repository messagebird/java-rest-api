package com.messagebird.objects.conversations;

import java.util.Map;

public class ConversationEmailRecipient {
    private String address;
    private String name;
    private Map<String, String> variables;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "ConversationEmailRecipient{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", variables=" + variables +
                '}';
    }
}
