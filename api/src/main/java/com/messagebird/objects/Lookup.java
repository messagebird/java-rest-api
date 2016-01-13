package com.messagebird.objects;

import java.io.Serializable;
import java.math.BigInteger;

public class Lookup implements Serializable {

    private static final long serialVersionUID = 8927014359452296030L;

    private String href;
    private String countryCode;
    private Integer countryPrefix;
    private BigInteger phoneNumber;
    private String type;
    private Formats formats;
    private LookupHlr hlr;

    @Override
    public String toString() {
        return "Lookup{" +
                "href=" + href +
                ", countryCode=" + countryCode +
                ", countryPrefix=" + countryPrefix +
                ", phoneNumber=" + phoneNumber +
                ", type=" + type +
                ", formats=" + formats +
                ", hlr=" + hlr +
                "}";
    }

    public Lookup() {
    }

    public Lookup(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHref() {
        return href;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getCountryPrefix() {
        return countryPrefix;
    }

    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return type;
    }

    public Formats getFormats() {
        return formats;
    }

    public Hlr getHlr() {
        return hlr;
    }

    static public class Formats implements Serializable {

        private static final long serialVersionUID = 2165916336570704972L;

        private String e164;
        private String international;
        private String national;
        private String rfc3966;

        public Formats() {
        }

        @Override
        public String toString() {
            return "Formats{" +
                    "e164=" + e164 +
                    ", international=" + international +
                    ", national=" + national +
                    ", rfc3966=" + rfc3966 +
                    '}';
        }

        public String getE164() {
            return e164;
        }

        public String getInternational() {
            return international;
        }

        public String getNational() {
            return national;
        }

        public String getRfc3966() {
            return rfc3966;
        }
    }
}
