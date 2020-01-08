package com.messagebird.objects;

import com.messagebird.objects.PhoneNumber;

import java.io.Serializable;
import java.util.List;

class PhoneNumbersResponse implements Serializable {
    private Number limit;
    private Number offset;
    private PhoneNumber[] items;

    @Override
    public String toString() {
        return "PhoneNumbersResponse{" +
            "limit=" + limit +
            ", offset=" + offset +
            ", items=" + items +
            "}";
    }
}