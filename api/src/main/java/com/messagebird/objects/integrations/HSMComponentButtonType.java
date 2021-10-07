package com.messagebird.objects.integrations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * HSMComponentButtonType
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#hsmcomponentbuttontype-object">HSMComponentButtonType</a>
 * @author ssk910
 */
public enum HSMComponentButtonType {

  PHONE_NUMBER("PHONE_NUMBER"),
  URL("URL"),
  QUICK_REPLY("QUICK_REPLY");

  private final String type;

  HSMComponentButtonType(String type) {
    this.type = type;
  }

  @JsonCreator
  public static HSMComponentButtonType forValue(String value) {
    for (HSMComponentButtonType hsmComponentButtonType : HSMComponentButtonType.values()) {
      if (hsmComponentButtonType.getType().equals(value)) {
        return hsmComponentButtonType;
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
