package com.messagebird.objects.conversations;

/**
 * Parameter for HSMs that localizes currencies.
 */
public class ConversationHsmLocalizableParameterCurrency {

    private String currencyCode;
    private int amount;

    /**
     * Instantiates a localizable parameter for currencies.
     *
     * @param code ISO 4217 compliant currency code.
     * @param amount Amount multiplied by 1000. E.g. 12.34 becomes 12340.
     */
    public ConversationHsmLocalizableParameterCurrency(final String code, final int amount) {
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    public ConversationHsmLocalizableParameterCurrency() {
        //
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ConversationHsmLocalizableParameterCurrency{" +
                "amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
