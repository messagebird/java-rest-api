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

	public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
    public void setItems(List<PurchasedNumber> items) {
        this.items = items;
    }

    public void setItems(PurchasedNumber... items) {
        this.items = List.of(items);
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
