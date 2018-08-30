package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Some of the template parameters related to date/time and currency are
 * localizable and can be displayed based on customer's device language and
 * local preferences. Default values are used when localization fails.
 */
public class ConversationHsmLocalizableParameter {

    @JsonProperty("default")
    private String defaultValue;

    private ConversationHsmLocalizableParameterCurrency currency;
    private Date dateTime;

    private ConversationHsmLocalizableParameter() {
        // Jackson requires an empty constructor for instantiation.
    }

    /**
     * Gets a parameter that does a simple replacement without localization.
     *
     * @param defaultValue String to replace parameter with.
     */
    public static ConversationHsmLocalizableParameter defaultValue(final String defaultValue) {
        ConversationHsmLocalizableParameter parameter = new ConversationHsmLocalizableParameter();
        parameter.defaultValue = defaultValue;

        return parameter;
    }

    /**
     * Gets a parameter that localizes a currency.
     *
     * @param defaultValue Default for when localization fails.
     * @param code ISO 4217 compliant currency code.
     * @param amount Amount multiplied by 1000. E.g. 12.34 becomes 12340.
     */
    public static ConversationHsmLocalizableParameter currency(final String defaultValue, final String code, final int amount) {
        ConversationHsmLocalizableParameter parameter = new ConversationHsmLocalizableParameter();
        parameter.defaultValue = defaultValue;
        parameter.currency = new ConversationHsmLocalizableParameterCurrency(code, amount);

        return parameter;
    }

    /**
     * Gets a parameter that localizes a date/time.
     *
     * @param defaultValue Default for when localization fails.
     * @param dateTime Localizable date/time.
     */
    public static ConversationHsmLocalizableParameter dateTime(final String defaultValue, final Date dateTime) {
        ConversationHsmLocalizableParameter parameter = new ConversationHsmLocalizableParameter();
        parameter.defaultValue = defaultValue;
        parameter.dateTime = dateTime;

        return parameter;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public ConversationHsmLocalizableParameterCurrency getCurrency() {
        return currency;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "ConversationHsmLocalizableParameter{" +
                "defaultValue='" + defaultValue + '\'' +
                '}';
    }
}
