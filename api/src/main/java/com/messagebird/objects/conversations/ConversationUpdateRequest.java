package com.messagebird.objects.conversations;

import java.util.Objects;

public class ConversationUpdateRequest {

    private final ConversationStatus status;

    public ConversationUpdateRequest(ConversationStatus status) {
        this.status = status;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationUpdateRequest that = (ConversationUpdateRequest) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }
}
