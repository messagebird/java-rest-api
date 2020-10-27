package com.messagebird.objects;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class PurchasedNumberCreatedResponse extends PurchasedNumber {
    private Date createdAt;
    private Date renewalAt;
}
