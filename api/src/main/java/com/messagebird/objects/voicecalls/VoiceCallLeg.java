package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * A leg describes a leg object (inbound or outbound) that belongs to a call.
 * At least one leg exists per call.
 * Inbound legs are being created when an incoming call to a Number is being initiated.
 * Outgoing legs are created when a call is transferred or when a call is being originated from the API.
 */
public class VoiceCallLeg {

    public final String id;
    public final String callID;
    public final String source;
    public final String destination;
    public final VoiceLegStatus status;
    public final VoiceLegDirection direction;
    public final BigDecimal cost;
    public final String currency;
    public final int duration;
    public final Date createdAt;
    public final Date updatedAt;
    public final Date answeredAt;
    public final Date endedAt;


    @JsonCreator
    public VoiceCallLeg(
        @JsonProperty("id") String id,
        @JsonProperty("callId") String callID,
        @JsonProperty("source") String source,
        @JsonProperty("destination") String destination,
        @JsonProperty("status") VoiceLegStatus status,
        @JsonProperty("direction") VoiceLegDirection direction,
        @JsonProperty("cost") BigDecimal cost,
        @JsonProperty("currency") String currency,
        @JsonProperty("duration") int duration,
        @JsonProperty("createdAt") Date createdAt,
        @JsonProperty("updatedAt") Date updatedAt,
        @JsonProperty("answeredAt") Date answeredAt,
        @JsonProperty("endedAt") Date endedAt
    ) {
        this.id = id;
        this.callID = callID;
        this.source = source;
        this.destination = destination;
        this.status = status;
        this.direction = direction;
        this.cost = cost;
        this.currency = currency;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.answeredAt = answeredAt;
        this.endedAt = endedAt;
    }

    @Override
    public String toString() {
        return "VoiceCallLeg{" +
                "id='" + id + '\'' +
                ", callID='" + callID + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", status=" + status +
                ", direction=" + direction +
                ", cost=" + cost +
                ", currency='" + currency + '\'' +
                ", duration=" + duration +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", answeredAt='" + answeredAt + '\'' +
                ", endedAt='" + endedAt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoiceCallLeg that = (VoiceCallLeg) o;
        return duration == that.duration &&
                Objects.equals(id, that.id) &&
                Objects.equals(callID, that.callID) &&
                Objects.equals(source, that.source) &&
                Objects.equals(destination, that.destination) &&
                status == that.status &&
                direction == that.direction &&
                Objects.equals(cost, that.cost) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(answeredAt, that.answeredAt) &&
                Objects.equals(endedAt, that.endedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, callID, source, destination, status, direction, cost, currency, duration, createdAt, updatedAt, answeredAt, endedAt);
    }
}
