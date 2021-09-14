package com.messagebird.objects.conversations;

import java.util.List;

public class MessageComponent {

    private MessageComponentType type;
    private String sub_type;
    private int index;
    private List<MessageParam> parameters;

    public void setType(MessageComponentType type) {
        this.type = type;
    }

    public MessageComponentType getType() {
        return type;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<MessageParam> getParameters() {
        return parameters;
    }

    public void setParameters(List<MessageParam> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "MessageComponent{" +
                "type='" + type + '\'' +
                ", sub_type='" + sub_type + '\'' +
                ", index=" + index +
                ", parameters=" + parameters +
                '}';
    }
}
