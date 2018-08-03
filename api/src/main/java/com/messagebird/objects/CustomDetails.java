package com.messagebird.objects;

/**
 * Custom fields of this contact.
 */
public class CustomDetails {

    private String custom1;
    private String custom2;
    private String custom3;
    private String custom4;

    public String getCustom1() {
        return custom1;
    }

    public String getCustom2() {
        return custom2;
    }

    public String getCustom3() {
        return custom3;
    }

    public String getCustom4() {
        return custom4;
    }

    public void setCustom1(String custom1) {
        this.custom1 = custom1;
    }

    public void setCustom2(String custom2) {
        this.custom2 = custom2;
    }

    public void setCustom3(String custom3) {
        this.custom3 = custom3;
    }

    public void setCustom4(String custom4) {
        this.custom4 = custom4;
    }

    @Override
    public String toString() {
        return "CustomDetails{" +
                "custom1='" + custom1 + '\'' +
                ", custom2='" + custom2 + '\'' +
                ", custom3='" + custom3 + '\'' +
                ", custom4='" + custom4 + '\'' +
                '}';
    }
}
