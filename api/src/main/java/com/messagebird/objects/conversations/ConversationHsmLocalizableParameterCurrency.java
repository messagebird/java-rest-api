package com.messagebird.objects.conversations;

import lombok.*;

/**
 * Parameter for HSMs that localizes currencies.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationHsmLocalizableParameterCurrency {

    private String currencyCode;
    private int amount;

}
