package com.messagebird.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;
import java.util.Date;

/**
 * MessageBird provides an API to send Network Queries to any mobile number across the world.
 *
 * An HLR allows you to view which mobile number (MSISDN) belongs to what operator in real time and see whether the number is active.
 * This object is returned after a Hlr request to MessageBird
 *
 * Created by rvt on 1/7/15.
 */
@Getter
@ToString
@NoArgsConstructor
public class Hlr {
    private String id;
    private String href;
    protected BigInteger msisdn;
    private String network;
    protected String reference;
    private String status;
    private Date createdDatetime;
    private Date statusDatetime;
    private HlrDetails details;
}

