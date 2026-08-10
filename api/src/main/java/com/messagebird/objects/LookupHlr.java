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

    @Override
    public BigInteger getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public void setPhoneNumber(BigInteger phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }

    @Override
    public void setReference(String reference) {
        super.setReference(reference);
    }
}