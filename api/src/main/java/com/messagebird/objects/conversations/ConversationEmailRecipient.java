package com.messagebird.objects.conversations;

import java.util.Map;

public class ConversationEmailRecipient {
    private String email;
    private String name;
    private Map<String, String> variables;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", variables=" + variables +
                '}';
    }
}
