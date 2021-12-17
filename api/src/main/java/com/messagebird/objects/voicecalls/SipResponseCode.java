package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * More details, including additional descriptions and common caused can be found here: https://developers.messagebird.com/api/voice-calling/#sip-status-codes
 * @author leandropinto
 *
 */
public enum SipResponseCode {
	//Successful
	OK,
	//The server understood the request, but is refusing to fulfill it.
	FORBIDDEN,
	//The server has definitive information that the user does not exist at the domain specified in the Request-URI.
	NOT_FOUND,
	//Couldn't find the user in time.
	REQUEST_TIMEOUT,
	//The user existed once, but is not available here any more.
	GONE,
	//Callee currently unavailable.
	TEMPORARILY_UNAVAILABLE,
	//Request-URI incomplete.
	ADDRESS_INCOMPLETE,
	//Callee is busy.
	BUSY_HERE,
	//Some aspect of the session description or the Request-URI is not acceptable.
	NOT_ACCEPTABLE_HERE,
	//The server could not fulfill the request due to some unexpected condition.
	INTERNAL_SERVER_ERROR,
	//The server does not have the ability to fulfill the request, such as because it does not recognize the request method.
	NOT_IMPLEMENTED,
	//The server is acting as a gateway or proxy, and received an invalid response from a downstream server while attempting to fulfill the request.
	BAD_GATEWAY,
	//The server is undergoing maintenance or is temporarily overloaded and so cannot process the request.
	SERVICE_UNAVAILABLE;

	@JsonCreator
	public static SipResponseCode forValue(Integer value) {
		switch (value) {
		case 200:
			return OK;
		case 403:
			return FORBIDDEN;
		case 404:
			return NOT_FOUND;
		case 408:
			return REQUEST_TIMEOUT;
		case 410:
			return GONE;
		case 480:
			return TEMPORARILY_UNAVAILABLE;
		case 484:
			return ADDRESS_INCOMPLETE;
		case 486:
			return BUSY_HERE;
		case 488:
			return NOT_ACCEPTABLE_HERE;
		case 500:
			return INTERNAL_SERVER_ERROR;
		case 501:
			return NOT_IMPLEMENTED;
		case 502:
			return BAD_GATEWAY;
		case 503:
			return SERVICE_UNAVAILABLE;

		default:
			throw new IllegalArgumentException("Unknown sip response code: " + value);
		}
	}
}