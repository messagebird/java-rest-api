package com.messagebird.objects;

import com.messagebird.exceptions.GeneralException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;

public class PurchasedNumbersFilter implements Serializable {
    private int limit = 10;
    private int offset = 0;
    private EnumSet<PhoneNumberFeature> features = EnumSet.noneOf(PhoneNumberFeature.class);
    private ArrayList<String> tags = new ArrayList<>();
    private String number;
    private String region;
    private String locality;
    private PhoneNumberType type;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public EnumSet<PhoneNumberFeature> getFeatures() {
        return features;
    }

    public void addFeature(PhoneNumberFeature... features) {
        Collections.addAll(this.features, features);
    }

    public void removeFeature(PhoneNumberFeature... features) {
        for (PhoneNumberFeature feature: features) {
            this.features.remove(feature);
        }
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void addTag(String... tags) {
        for (String tag: tags) {
            if (!this.tags.contains(tag)) {
                this.tags.add(tag);
            }
        }
    }

    public void removeTag(String... tags) {
        for (String tag: tags) {
            this.tags.remove(tag);
        }
    }

    public void clearTags() {
        this.tags.clear();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public PhoneNumberType getType() {
        return type;
    }

    public void setType(PhoneNumberType type) {
        this.type = type;
    }

    public HashMap<String, Object> toHashMap() throws GeneralException {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        for (Field f: getClass().getDeclaredFields()) {
            if (f.canAccess(this)) {
                try {
                    Object value = f.get(this);
                    String key = f.getName();
                    if (value != null) {
                        map.put(key, value);
                    }
                } catch (IllegalAccessException exception) {
                    throw new GeneralException("Error converting to HashMap.");
                }
            }
        }
        return map;
    }

    @Override
    public String toString() {
        return "PurchasedNumbersFilter{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", features=" + features +
                ", tags=" + tags +
                ", number='" + number + '\'' +
                ", region='" + region + '\'' +
                ", locality='" + locality + '\'' +
                ", type=" + type +
                '}';
    }
}
