package com.messagebird.objects;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.objects.PhoneNumberFeature;
import com.messagebird.objects.PhoneNumberType;
import com.messagebird.objects.PhoneNumberSearchPattern;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.lang.reflect.Field;

public class PhoneNumbersLookup {

    private Number number;
    private Number limit;
    private EnumSet<PhoneNumberFeature> features;
    private PhoneNumberType type;
    private PhoneNumberSearchPattern searchPattern;

    public Number getNumber() {
        return this.number;
    }

    public EnumSet<PhoneNumberFeature> getFeatures() {
        return this.features;
    }

    public PhoneNumberType getType() {
        return this.type;
    }

    public Number getLimit() {
        return this.limit;
    }

    public PhoneNumberSearchPattern getSearchPattern() {
        return this.searchPattern;
    }

	public void setNumber(Number number) {
        this.number = number;
    }
    
    public void setFeatures(EnumSet<PhoneNumberFeature> features) {
        this.features = features;
    }

    public void setFeatures(PhoneNumberFeature... features) {
        EnumSet<PhoneNumberFeature> featuresEnum = EnumSet.noneOf(PhoneNumberFeature.class);
        featuresEnum.addAll(Arrays.asList(features));
        this.features = featuresEnum;
    }
    
    public void setType(PhoneNumberType type) {
        this.type = type;
    }
    
    public void setLimit(Number limit) {
        this.limit = limit;
    }

    public void setSearchPattern(PhoneNumberSearchPattern searchPattern) {
        this.searchPattern = searchPattern;
    }

    public HashMap<String, Object> toHashMap() throws GeneralException {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        for (Field f: getClass().getDeclaredFields()) {
            try {
                Object value = f.get(this);
                String key = f.getName();
                if (value != null) {
                    map.put(key, value);
                }
            } catch (IllegalAccessException exception) {
                throw new GeneralException("Error Converting PhoneNumbersLookup Class to HashMap.");
            }
        }
        return map;
    }

    @Override
    public String toString() {
        return "PhoneNumbersLookup{" +
            " number='" + number + "'" +
            ", features='" + features + "'" +
            ", type='" + type + "'" +
            ", limit='" + limit + "'" +
            ", searchPattern='" + searchPattern + "'" +
            "}";
    }    
}