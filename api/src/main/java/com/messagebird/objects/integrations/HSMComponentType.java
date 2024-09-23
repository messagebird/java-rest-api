package com.messagebird.objects.integrations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

/**
 * An enum for HSMComponentType
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#hsmcomponenttype-object">HSMComponentType</a>
 */
public enum HSMComponentType {
  BODY("BODY"),
  HEADER("HEADER"),
  FOOTER("FOOTER"),
  BUTTONS("BUTTONS"),
  CAROUSEL("CAROUSEL"),
  LIMITED_TIME_OFFER("LIMITED_TIME_OFFER");

  private static final Map<String, HSMComponentType> TYPE_MAP;

  static {
    Map<String, HSMComponentType> map = new HashMap<>();
    for (HSMComponentType hsmComponentType : HSMComponentType.values()) {
      map.put(hsmComponentType.getType().toLowerCase(), hsmComponentType);
    }
    TYPE_MAP = Collections.unmodifiableMap(map);
  }

  private final String type;

  HSMComponentType(String type) {
    this.type = type;
  }

  @JsonCreator
  public static HSMComponentType forValue(String value) {
    Objects.requireNonNull(value, "Value cannot be null");
    return TYPE_MAP.get(value.toLowerCase(Locale.ROOT));
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
