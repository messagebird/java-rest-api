package com.messagebird.objects;

import java.util.List;

/**
 * Provides an object to deserialize to for endpoints returning listings.
 *
 * @param <T> Type of the items, e.g. Contact or Message.
 */
public class ListBase<T> {

    private Integer offset;
    private Integer limit;
    private Integer totalCount;
    private Links links;

    public ListBase() {
    }

    @Override
    public String toString() {
        return "ListBase{" +
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

    public void setItems(List<T> items) {
        this.items = items;
    }
}
