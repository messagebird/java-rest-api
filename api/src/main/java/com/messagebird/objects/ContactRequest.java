package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Contains writable values for Contact objects.
 */
public class ContactRequest {

    private String msisdn;
    private String firstName;
    private String lastName;

    // Flatten the customDetails object to the Contact when serializing,
    // see: https://developers.messagebird.com/docs/contacts#create-a-contact
    @JsonUnwrapped
    private CustomDetails customDetails;

    public ContactRequest(String msisdn, String firstName, String lastName, CustomDetails customDetails) {
        this.msisdn = msisdn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customDetails = customDetails;
    }

    public ContactRequest(String msisdn, String firstName, String lastName) {
        this.msisdn = msisdn;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ContactRequest(String msisdn) {
        this.msisdn = msisdn;
    }

    public ContactRequest() {
        //
    }

    /**
     * The phone number of contact.
     */
    public String getMsisdn() {
        return msisdn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CustomDetails getCustomDetails() {
        return customDetails;
    }

    /**
     * The phone number of contact.
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCustomDetails(CustomDetails customDetails) {
        this.customDetails = customDetails;
    }

    @Override
    public String toString() {
        return "ContactRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", customDetails=" + customDetails +
                '}';
    }
}
