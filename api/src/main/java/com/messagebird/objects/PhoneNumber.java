package com.messagebird.objects;

import com.messagebird.objects.PhoneNumberFeature;

import java.util.EnumSet;
import java.util.List;

public class PhoneNumber {
    private String number;
    private String country;
    private String region;
    private String locality;
    private EnumSet<PhoneNumberFeature> features;
    private String type;
    private List<String> tags;

    public String getNumber() {
        return this.number;
    }

    public String getCountry() {
        return this.country;
    }

    public String getRegion() {
        return this.region;
    }

    public String getLocality() {
        return this.locality;
    }

    public EnumSet<PhoneNumberFeature> getFeatures() {
        return this.features;
    }

    public String getType() {
        return this.type;
    }

    public List<String> getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
            "number='" + number + "\'" +
            ", country='" + country + "\'" +
            ", region='" + region + "\'" +
            ", locality='" + locality + "\'" +
            ", features=" + features +
            ", tags=" + tags +
            ", type='" + type + "\'" +
            "}";
    }
}
