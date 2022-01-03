package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum  MessageComponentType {

  HEADER("header"),
  BODY("body"),
  FOOTER("footer"),
  BUTTON("button");


  private final String type;

  MessageComponentType(final String type) {
    this.type = type;
  }

  @JsonCreator
  public static MessageComponentType forValue(String value) {
    for (MessageComponentType  componentType: MessageComponentType.values()) {
      if (componentType.getType().equals(value)) {
        return componentType;
      }
    }

    return null;
  }

  @JsonValue
  public String toJson() {
    return getType();
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return getType();
  }
}
