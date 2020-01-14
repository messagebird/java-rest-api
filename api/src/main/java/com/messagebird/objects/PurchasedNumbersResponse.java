package com.messagebird.objects;

import java.util.List;

public class PurchasedNumbersResponse {
    private int offset;
    private int limit;
    private int count;
    private int totalCount;
    private List<PurchasedNumber> items;

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getCount() {
        return count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<PurchasedNumber> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "PurchasedNumbersResponse{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", count=" + count +
                ", totalCount=" + totalCount +
                ", items=" + items +
                '}';
    }
}
