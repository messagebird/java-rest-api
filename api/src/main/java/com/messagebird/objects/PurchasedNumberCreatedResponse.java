package com.messagebird.objects;

import java.util.Date;

public class PurchasedNumberCreatedResponse extends PurchasedNumber {
    private Date createdAt;
    private Date renewalAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getRenewalAt() {
        return renewalAt;
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
