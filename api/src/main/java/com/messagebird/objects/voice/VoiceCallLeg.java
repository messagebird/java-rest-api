package com.messagebird.objects.voice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class VoiceCallLeg {

    public final String id;
    public final String callID;
    public final String source;
    public final String destination;
    public final LegStatus status;
    public final LegDirection direction;
    public final BigDecimal cost;
    public final String currency;
    public final int duration;
    public final String createdAt;
    public final String updatedAt;
    public final String answeredAt;
    public final String endedAt;

    public enum LegStatus {
        Starting,
        Ringing,
        Ongoing,
        Busy,
        NoAnswer,
        Failed,
        Hangup;

        @JsonCreator
        public static LegStatus fromString(String value) {
            switch (value) {
                case "starting": return Starting;
                case "ringing": return Ringing;
                case "ongoing": return Ongoing;
                case "busy": return Busy;
                case "no_answer": return NoAnswer;
                case "failed": return Failed;
                case "hangup": return Hangup;
                default: throw new IllegalArgumentException("Unknown leg status: " + value);
            }
        }
    }

    public enum LegDirection {
        Incoming, Outgoing;

        @JsonCreator
        public static LegDirection fromString(String value) {
            switch (value) {
                case "incoming": return Incoming;
                case "outgoing": return Outgoing;
                default: throw new IllegalArgumentException("Unknown leg direction: " + value);
            }
        }
    }

    @JsonCreator
    public VoiceCallLeg(
        @JsonProperty("id") String id,
        @JsonProperty("callId") String callID,
        @JsonProperty("source") String source,
        @JsonProperty("destination") String destination,
        @JsonProperty("status") LegStatus status,
        @JsonProperty("direction") LegDirection direction,
        @JsonProperty("cost") BigDecimal cost,
        @JsonProperty("currency") String currency,
        @JsonProperty("duration") int duration,
        @JsonProperty("createdAt") String createdAt,
        @JsonProperty("updatedAt") String updatedAt,
        @JsonProperty("answeredAt") String answeredAt,
        @JsonProperty("endedAt") String endedAt
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
