package com.messagebird.objects.conversations;

import lombok.*;

/**
 * Coordinates that point to a location.
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConversationContentLocation {

    private double latitude, longitude;

}
