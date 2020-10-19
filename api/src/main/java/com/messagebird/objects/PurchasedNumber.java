package com.messagebird.objects;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PurchasedNumber extends PhoneNumber {
    private List<String> tags;
    private String status;

}
