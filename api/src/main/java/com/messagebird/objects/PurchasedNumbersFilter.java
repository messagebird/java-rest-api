package com.messagebird.objects;

import com.messagebird.exceptions.GeneralException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;

@ToString
@Getter
@Setter
public class PurchasedNumbersFilter implements Serializable {
    private int limit = 10;
    private int offset = 0;
    private EnumSet<PhoneNumberFeature> features = EnumSet.noneOf(PhoneNumberFeature.class);
    private ArrayList<String> tags = new ArrayList<>();
    private String number;
    private String region;
    private String locality;
    private PhoneNumberType type;

    public void addFeature(PhoneNumberFeature... features) {
        Collections.addAll(this.features, features);
    }

    public void removeFeature(PhoneNumberFeature... features) {
        for (PhoneNumberFeature feature : features) {
            this.features.remove(feature);
        }
    }

    public void addTag(String... tags) {
        for (String tag : tags) {
            if (!this.tags.contains(tag)) {
                this.tags.add(tag);
            }
        }
    }

    public void removeTag(String... tags) {
        for (String tag : tags) {
            this.tags.remove(tag);
        }
    }

    public void clearTags() {
        this.tags.clear();
    }

    public HashMap<String, Object> toHashMap() throws GeneralException {
        final HashMap<String, Object> map = new HashMap<>();
        for (Field f : getClass().getDeclaredFields()) {
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
        return map;
    }
}
