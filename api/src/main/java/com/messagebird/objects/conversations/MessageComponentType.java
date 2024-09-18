package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public enum  MessageComponentType {

  HEADER("header"),
  BODY("body"),
  FOOTER("footer"),
  BUTTON("button"),
  CARD("card"),
  CAROUSEL("carousel"),
  LIMITED_TIME_OFFER("limited_time_offer");

  private static final Map<String, MessageComponentType> TYPE_MAP;

  static {
    Map<String, MessageComponentType> map = new HashMap<>();
    for (MessageComponentType componentType : MessageComponentType.values()) {
      map.put(componentType.getType().toLowerCase(), componentType);
    }
    TYPE_MAP = Collections.unmodifiableMap(map);
  }

  private final String type;

  MessageComponentType(final String type) {
    this.type = type;
  }

  @JsonCreator
  public static MessageComponentType forValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Value cannot be null");
    }
    return TYPE_MAP.get(value.toLowerCase());
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
