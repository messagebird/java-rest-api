package com.messagebird.objects;

import com.messagebird.exceptions.GeneralException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;

@ToString
@Getter
@Setter
public class PhoneNumbersLookup {

    private Number number;
    private Number limit;
    private EnumSet<PhoneNumberFeature> features;
    private PhoneNumberType type;
    private PhoneNumberSearchPattern searchPattern;


    public void setFeatures(PhoneNumberFeature... features) {
        EnumSet<PhoneNumberFeature> featuresEnum = EnumSet.noneOf(PhoneNumberFeature.class);
        featuresEnum.addAll(Arrays.asList(features));
        this.features = featuresEnum;
    }
    
    public HashMap<String, Object> toHashMap() throws GeneralException {
        final HashMap<String, Object> map = new HashMap<>();
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
}