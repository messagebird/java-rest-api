package com.messagebird.objects;

import java.util.List;

/**
 * Base class for retrieval of message lists
 *
 * Created by rvt on 1/8/15.
 */
public class MessageListBase<T> {
    private Integer offset;
    private Integer limit;
    private Integer totalCount;
    private Links links;

    public MessageListBase() {
    }

    @Override
    public String toString() {
        return "MessageListBase{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", totalCount=" + totalCount +
                ", links=" + links +
                ", items=" + items +
                '}';
    }

    private List<T> items;

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public Links getLinks() {
        return links;
    }

    public List<T> getItems() {
        return items;
    }
}
