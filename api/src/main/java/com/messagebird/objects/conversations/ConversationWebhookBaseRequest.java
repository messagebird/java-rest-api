package com.messagebird.objects.conversations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Contains common fields for webhook requests.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public abstract class ConversationWebhookBaseRequest {
    protected String url;
    protected List<ConversationWebhookEvent> events;

    protected abstract String getRequestName();
    protected abstract String getStringRepresentationOfExtraParameters();

}
