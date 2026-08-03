package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@code status.metadata} block delivered inside status webhook payloads
 * (e.g. {@code statusSent}, {@code statusDelivered}).
 *
 * <p>This class is not produced by any SDK request — it is a standalone POJO
 * intended for consumers who deserialize incoming webhook payloads in their own
 * HTTP handlers. Use it via {@code ObjectMapper.readValue(body, ...)}.
 *
 * <p>Note the mixed casing of this object. {@code pricing} and
 * {@code conversation} are near-verbatim passthroughs of Meta's own objects and
 * so keep their snake_case keys ({@code pricing_model}, {@code category}, …);
 * they are exposed here as raw maps rather than modelled types, because their
 * contents track Meta's schema rather than ours. {@code recipient} is ours and
 * follows the camelCase convention used everywhere else in the API. Any other
 * key present on the payload — for example {@code biz_opaque_callback_data} —
 * is collected into {@link #getAdditionalProperties()} rather than dropped.
 *
 * <p>{@code recipient} is absent from payloads for accounts that never receive
 * BSUIDs, in which case {@link #getRecipient()} returns {@code null}.
 */
public class ConversationStatusMetadata {

    private Map<String, Object> pricing;
    private Map<String, Object> conversation;
    private ConversationRecipientMetadata recipient;
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();

    public Map<String, Object> getPricing() {
        return pricing;
    }

    public void setPricing(Map<String, Object> pricing) {
        this.pricing = pricing;
    }

    public Map<String, Object> getConversation() {
        return conversation;
    }

    public void setConversation(Map<String, Object> conversation) {
        this.conversation = conversation;
    }

    public ConversationRecipientMetadata getRecipient() {
        return recipient;
    }

    public void setRecipient(ConversationRecipientMetadata recipient) {
        this.recipient = recipient;
    }

    /**
     * Every key on the payload that has no dedicated accessor above, in the
     * order it was encountered. Empty when the payload holds nothing else.
     *
     * @return the unmodelled remainder of the metadata object
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "ConversationStatusMetadata{" +
                "pricing=" + pricing +
                ", conversation=" + conversation +
                ", recipient=" + recipient +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
