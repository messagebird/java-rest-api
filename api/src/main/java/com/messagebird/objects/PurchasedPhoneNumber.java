package com.messagebird.objects;

import java.util.Date;
import java.util.List;

public class PurchasedPhoneNumber extends PhoneNumber {
    private List<String> tags;
    private String status;
    private Date createdAt;
    private Date renewalAt;

    public List<String> getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
    }

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
            ", tags='" + tags + "\'" +
            ", status='" + status + "\'" +
            ", createdAt='" + createdAt + "\'" +
            ", renewalAt='" + renewalAt + "\'" +
            "}";
    }
}
