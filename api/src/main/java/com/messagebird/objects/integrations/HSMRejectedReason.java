package com.messagebird.objects.integrations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An enum class for HSMRejectedReason
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#hsmrejectedreason-object">HSMRejectedReason object</a>
 * @author ssk910
 */
public enum HSMRejectedReason {

  ABUSIVE_CONTENT("ABUSIVE_CONTENT"),
  INVALID_FORMAT("INVALID_FORMAT"),
  NONE("NONE"),
  PROMOTIONAL("PROMOTIONAL"),
  TAG_CONTENT_MISMATCH("TAG_CONTENT_MISMATCH"),
  NON_TRANSIENT_ERROR("NON_TRANSIENT_ERROR");

  private final String rejectedReason;

  HSMRejectedReason(String rejectedReason) {
    this.rejectedReason = rejectedReason;
  }

  @JsonCreator
  public static HSMRejectedReason forValue(String value) {
    for (HSMRejectedReason hsmRejectedReason : HSMRejectedReason.values()) {
      if (hsmRejectedReason.getRejectedReason().equals(value)) {
        return hsmRejectedReason;
      }
    }

    return null;
  }

  @JsonValue
  public String toJson() {
    return getRejectedReason();
  }

  public String getRejectedReason() {
    return rejectedReason;
  }

  @Override
  public String toString() {
    return getRejectedReason();
  }
}
