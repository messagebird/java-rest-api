package com.messagebird.objects.integrations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An enum for HSMComponentType
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#hsmcomponenttype-object">HSMComponentType</a>
 */
public enum HSMComponentType {
  BODY("BODY"),
  HEADER("HEADER"),
  FOOTER("FOOTER"),
  BUTTONS("BUTTONS");

  private final String type;

  HSMComponentType(String type) {
    this.type = type;
  }

  @JsonCreator
  public static HSMComponentType forValue(String value) {
    for (HSMComponentType hsmComponentType : HSMComponentType.values()) {
      if (hsmComponentType.getType().equals(value)) {
        return hsmComponentType;
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
