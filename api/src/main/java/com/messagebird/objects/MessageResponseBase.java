package com.messagebird.objects;

/**
 * Created by rvt on 1/7/15.
 */
public interface MessageResponseBase {
    /**
     * An unique random ID which is created on the MessageBird platform and is returned upon creation of the object.
     * @return
     */
    String getId();

    /**
     * The URL of the created object.
     * @return
     */
    String getHref();

    /**
     * The body of the SMS message.
     * @return
     */
    String getBody();

    /**
     * The reference that was set for the message
     * @return
     */
    String getReference();

    /**
     * Recipient information.
     * @return
     */
    MessageResponse.Recipients getRecipients();
}
