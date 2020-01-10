package com.messagebird.objects;

import com.messagebird.objects.PhoneNumberFeature;

import java.util.EnumSet;

public class PhoneNumber {
    private String number;
    private String country;
    private String region;
    private String locality;
    private EnumSet<PhoneNumberFeature> features;
    private String type;

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

    @Override
    public String toString() {
        return "PhoneNumber{" +
            "number='" + number + "\'" +
            ", country='" + country + "\'" +
            ", region='" + region + "\'" +
            ", locality='" + locality + "\'" +
            ", features=" + features +
            ", type='" + type + "\'" +
            "}";
    }
}
