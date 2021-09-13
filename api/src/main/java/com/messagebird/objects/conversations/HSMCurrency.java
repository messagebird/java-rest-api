package com.messagebird.objects.conversations;

public class HSMCurrency {

    private String currencyCode;
    private int amount;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "HSMCurrency{" +
                "currencyCode='" + currencyCode + '\'' +
                ", amount=" + amount +
                '}';
    }
}
