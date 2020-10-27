package com.messagebird.objects;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

public class LookupHlr extends Hlr {
    @Getter
    @Setter
    private String countryCode;

    public BigInteger getPhoneNumber() {
        return msisdn;
    }

    public void setPhoneNumber(BigInteger phoneNumber) {
        this.msisdn = phoneNumber;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
