package com.messagebird.objects;

import com.messagebird.objects.PhoneNumber;

import java.util.List;

import java.io.Serializable;

public class PhoneNumbersResponse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6177098534499444839L;
    private Number limit;
    private Number offset;
    private List<PhoneNumber> items;

    public Number getLimit() {
        return this.limit;
    }

    public Number getOffset() {
        return this.offset;
    }

    public List<PhoneNumber> getItems() {
        return this.items;
    }
    
    @Override
    public String toString() {
        return "PhoneNumbersResponse{" +
            "limit=" + limit +
            ", offset=" + offset +
            ", items=" + items +
            "}";
    }
}