package com.messagebird.objects;

import java.util.Date;
import java.util.EnumSet;

public class PurchasedNumberCreatedResponse extends PurchasedNumber {
    public PurchasedNumberCreatedResponse(String number, String country, String region, String locality,
            EnumSet<PhoneNumberFeature> features, String type) {
        super(number, country, region, locality, features, type);
    }

    private Date createdAt;
    private Date renewalAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getRenewalAt() {
        return renewalAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public void setRenewalAt(Date renewalAt) {
        this.renewalAt = renewalAt;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
            "number='" + this.getNumber() + "\'" +
            ", country='" + this.getCountry() + "\'" +
            ", region='" + this.getRegion() + "\'" +
            ", locality='" + this.getLocality() + "\'" +
            ", features=" + this.getFeatures() +
            ", type='" + this.getType() + "\'" +
            ", tags='" + this.getTags() + "\'" +
            ", status='" + this.getStatus() + "\'" +
            ", createdAt='" + createdAt + "\'" +
            ", renewalAt='" + renewalAt + "\'" +
            "}";
    }
}
