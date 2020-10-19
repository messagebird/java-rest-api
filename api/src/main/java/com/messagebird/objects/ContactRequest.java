package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains writable values for Contact objects.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    private String msisdn;
    private String firstName;
    private String lastName;

    // Flatten the customDetails object to the Contact when serializing,
    // see: https://developers.messagebird.com/docs/contacts#create-a-contact
    @JsonUnwrapped
    private CustomDetails customDetails;

    public ContactRequest(String msisdn) {
        this.msisdn = msisdn;
    }
}
