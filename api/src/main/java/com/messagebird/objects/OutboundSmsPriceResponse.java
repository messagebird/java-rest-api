package com.messagebird.objects;

import java.util.List;

public class OutboundSmsPriceResponse {
    private int gateway;
    private String currencyCode;
    private int totalCount;
    private List<OutboundSmsPrice> prices;

    public int getGateway() {
        return gateway;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<OutboundSmsPrice> getPrices() {
        return prices;
    }

    @Override
    public String toString() {
        return "OutboundSmsPriceResponse{" +
                "gateway=" + gateway +
                ", currencyCode='" + currencyCode + '\'' +
                ", totalCount=" + totalCount +
                ", prices=" + prices +
                '}';
    }
}
