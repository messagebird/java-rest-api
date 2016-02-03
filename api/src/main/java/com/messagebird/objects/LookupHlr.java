package com.messagebird.objects;

import java.math.BigInteger;

public class LookupHlr extends Hlr {
    private String countryCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

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
