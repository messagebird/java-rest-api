package com.messagebird.objects;

import lombok.Getter;
import lombok.ToString;

import java.util.EnumSet;

@ToString
@Getter
public class PhoneNumber {
    private String number;
    private String country;
    private String region;
    private String locality;
    private EnumSet<PhoneNumberFeature> features;
    private String type;
}
