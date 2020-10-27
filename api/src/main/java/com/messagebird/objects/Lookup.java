package com.messagebird.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;

@Getter
@NoArgsConstructor
@ToString
public class Lookup implements Serializable {

    private static final long serialVersionUID = 8927014359452296030L;

    private String href;
    @Setter
    private String countryCode;
    private Integer countryPrefix;
    private BigInteger phoneNumber;
    private String type;
    private Formats formats;
    private LookupHlr hlr;

    public Lookup(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NoArgsConstructor
    @ToString
    @Getter
    static public class Formats implements Serializable {

        private static final long serialVersionUID = 2165916336570704972L;

        private String e164;
        private String international;
        private String national;
        private String rfc3966;
    }
}
