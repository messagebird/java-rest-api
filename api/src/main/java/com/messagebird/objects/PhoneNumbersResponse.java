package com.messagebird.objects;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@ToString
public class PhoneNumbersResponse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6177098534499444839L;
    private Number limit;
    private Number offset;
    private List<PhoneNumber> items;

}