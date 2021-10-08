package com.messagebird.objects.integrations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An enum for HSMComponentFormat
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#hsmcomponentformat-object">HSMComponentFormat</a>
 * @author ssk910
 */
public enum HSMComponentFormat {

  TEXT("TEXT"),
  IMAGE("IMAGE"),
  DOCUMENT("DOCUMENT"),
  VIDEO("VIDEO");

  private final String format;

  HSMComponentFormat(String format) {
    this.format = format;
  }

  @JsonCreator
  public static HSMComponentFormat forValue(String value) {
    for (HSMComponentFormat hsmComponentFormat : HSMComponentFormat.values()) {
      if (hsmComponentFormat.getFormat().equals(value)) {
        return hsmComponentFormat;
      }
    }

    return null;
  }

  @JsonValue
  public String toJson() {
    return getFormat();
  }

  public String getFormat() {
    return format;
  }

  @Override
  public String toString() {
    return getFormat();
  }
}
