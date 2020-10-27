package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

/**
 * Some of the template parameters related to date/time and currency are
 * localizable and can be displayed based on customer's device language and
 * local preferences. Default values are used when localization fails.
 */
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationHsmLocalizableParameter {

    @JsonProperty("default")
    private String defaultValue;

    private ConversationHsmLocalizableParameterCurrency currency;
    private Date dateTime;

}
