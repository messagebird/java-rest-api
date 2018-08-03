package com.messagebird.objects;

import java.util.List;

/**
 * Base class for retrieval of message lists
 *
 * Created by rvt on 1/8/15.
 */
public class MessageListBase<T> extends ListBase<T> {

    // Only here to maintain backwards compatibility, for those relying on
    // MessageListBase. If you're looking to create a new list type, you'll
    // probably want to go with ListBase instead.

    @Override
    public String toString() {
        return "MessageListBase{" +
                "offset=" + getOffset() +
                ", limit=" + getLimit() +
                ", totalCount=" + getTotalCount() +
                ", links=" + getLinks() +
                ", items=" + getItems() +
                '}';
    }
}
