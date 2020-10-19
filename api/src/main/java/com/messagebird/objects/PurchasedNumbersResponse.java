package com.messagebird.objects;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PurchasedNumbersResponse {
    private int offset;
    private int limit;
    private int count;
    private int totalCount;
    private List<PurchasedNumber> items;
}
