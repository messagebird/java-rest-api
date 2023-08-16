package com.messagebird.objects;

import java.math.BigDecimal;

public class OutboundSmsPrice {
    private BigDecimal price;
    private String currencyCode;
    private String mccmnc;
    private String mcc;
    private String mnc;
    private String countryName;
    private String countryIsoCode;
    private String operatorName;

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getMccmnc() {
        return mccmnc;
    }

    public String getMcc() {
        return mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    @Override
    public String toString() {
        return "OutboundSmsPrice{" +
                "price=" + price +
                ", currencyCode='" + currencyCode + '\'' +
                ", mccmnc='" + mccmnc + '\'' +
                ", mcc='" + mcc + '\'' +
                ", mnc='" + mnc + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryIsoCode='" + countryIsoCode + '\'' +
                ", operatorName='" + operatorName + '\'' +
                '}';
    }
}
