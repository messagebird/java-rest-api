package com.messagebird.objects.conversations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * Webhooks enable real-time notifications of conversation events to be
 * delivered to endpoints on your own server. A webhook can be configured with
 * a URL and a list of events that should be subscribed to for notifications.
 * It's possible to create multiple webhooks with different URLs to listen to
 * one or more events each.
 */
@Getter
@Setter
@ToString
public class ConversationWebhook {

    private String id;
    private String channelId;
    private String url;
    private ConversationWebhookStatus status;
    private List<ConversationWebhookEvent> events;
    private Date createdDatetime;
    private Date updatedDatetime;

}
