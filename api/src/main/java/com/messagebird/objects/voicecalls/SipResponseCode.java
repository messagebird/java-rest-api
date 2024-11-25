package com.messagebird.objects.voicecalls;

import java.util.HashMap;
import java.util.Map;

/**
 * More details, including additional descriptions and common caused can be found here: https://developers.messagebird.com/api/voice-calling/#sip-status-codes
 * @author leandropinto
 *
 */
public enum SipResponseCode {
	//Successful
	OK(200),
	//The server understood the request, but is refusing to fulfill it.
	FORBIDDEN(403),
	//The server has definitive information that the user does not exist at the domain specified in the Request-URI.
	NOT_FOUND(404),
	//Couldn't find the user in time.
	REQUEST_TIMEOUT(408),
	//The user existed once, but is not available here any more.
	GONE(410),
	//Callee currently unavailable.
	TEMPORARILY_UNAVAILABLE(480),
	//Request-URI incomplete.
	ADDRESS_INCOMPLETE(484),
	//Callee is busy.
	BUSY_HERE(486),
	//Some aspect of the session description or the Request-URI is not acceptable.
	NOT_ACCEPTABLE_HERE(488),
	//The server could not fulfill the request due to some unexpected condition.
	INTERNAL_SERVER_ERROR(500),
	//The server does not have the ability to fulfill the request, such as because it does not recognize the request method.
	NOT_IMPLEMENTED(501),
	//The server is acting as a gateway or proxy, and received an invalid response from a downstream server while attempting to fulfill the request.
	BAD_GATEWAY(502),
	//The server is undergoing maintenance or is temporarily overloaded and so cannot process the request.
	SERVICE_UNAVAILABLE(503);

	private static final Map<Integer, SipResponseCode> codeToResponse = new HashMap<>();

	static {
		for (SipResponseCode responseCode : values()) {
			codeToResponse.put(responseCode.value, responseCode);
		}
	}

	private final int value;

	SipResponseCode(int value) {
		this.value = value;
	}

	public static SipResponseCode forValue(int value) {
		SipResponseCode responseCode = codeToResponse.get(value);
		if (responseCode == null) {
			throw new IllegalArgumentException("Unknown sip response code: " + value);
		}
		return responseCode;
	}

	public int getValue() {
		return value;
	}
}