package com.messagebird.objects;

import java.util.Date;

/**
 * Created by rvt on 1/7/15.
 */
public interface MessageBase {
    /**
     * The body of the SMS message.
     * @return Message body
     */
    String getBody();

    /**
     * The list of recipients msisdn's.
     * @return String of comma separated recipients
     */
    String getRecipients();

    /**
     * @return your own reference
     */
    String getReference();

    /**
     * The scheduled date and time of the message
     * @return the configured date/time to send the mesage, this is handled on the message bird platform
     */
    Date getScheduledDatetime();
}
