package com.messagebird.objects.voicecalls;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;

/**
 * A leg describes a leg object (inbound or outbound) that belongs to a call.
 * At least one leg exists per call.
 * Inbound legs are being created when an incoming call to a Number is being initiated.
 * Outgoing legs are created when a call is transferred or when a call is being originated from the API.
 */
@Value
@Getter(value = AccessLevel.NONE)
public class VoiceCallLeg {

    public String id;
    public String callId;
    public String source;
    public String destination;
    public VoiceLegStatus status;
    public VoiceLegDirection direction;
    public BigDecimal cost;
    public String currency;
    public int duration;
    public Date createdAt;
    public Date updatedAt;
    public Date answeredAt;
    public Date endedAt;

}
