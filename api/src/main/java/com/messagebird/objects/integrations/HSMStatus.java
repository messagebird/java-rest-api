package com.messagebird.objects.integrations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An enum for HSMStatus object
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#hsmstatus-object">HSMStatus object</a>
 * @author ssk910
 */
public enum HSMStatus {

  NEW("NEW"),
  APPROVED("APPROVED"),
  PENDING("PENDING"),
  REJECTED("REJECTED"),
  PENDING_DELETION("PENDING_DELETION"),
  DELETED("DELETED"),
  DISABLED("DISABLED"),
  PAUSED("PAUSED");

  private final String status;

  HSMStatus(String status) {
    this.status = status;
  }

  @JsonCreator
  public static HSMStatus forValue(String value) {
    for (HSMStatus hsmStatus : HSMStatus.values()) {
      if (hsmStatus.getStatus().equals(value)) {
        return hsmStatus;
      }
    }

    return null;
  }

  @JsonValue
  public String toJson() {
    return getStatus();
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return getStatus();
  }
}
