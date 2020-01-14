package com.messagebird.objects;

import java.util.List;

public class PurchasedNumber extends PhoneNumber {
    private List<String> tags;
    private String status;

    public List<String> getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
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
            "}";
    }
}
