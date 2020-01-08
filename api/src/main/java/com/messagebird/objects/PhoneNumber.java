package com.messagebird.objects;

import com.messagebird.objects.PhoneNumberFeature;

import java.io.Serializable;
import java.util.EnumSet;

// TODO: Debug or change EnumSet to Array of Strings?
public class PhoneNumber {
    private String number;
    private String country;
    private String region;
    private String locality;
    private EnumSet<PhoneNumberFeature> features;
    private String type;

    public PhoneNumber(String number, String country, String region, EnumSet<PhoneNumberFeature> features, String type) {
        this.number = number;
        this.country = country;
        this.region = region;
        this.features = features;
        this.type = type;
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
