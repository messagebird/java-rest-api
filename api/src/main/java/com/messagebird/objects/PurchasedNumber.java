package com.messagebird.objects;

import java.util.EnumSet;
import java.util.List;

public class PurchasedNumber extends PhoneNumber {
    public PurchasedNumber(String number, String country, String region, String locality,
            EnumSet<PhoneNumberFeature> features, String type) {
        super(number, country, region, locality, features, type);
    }

    private List<String> tags;
    private String status;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setTags(String... tags) {
        this.tags = List.of(tags);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
