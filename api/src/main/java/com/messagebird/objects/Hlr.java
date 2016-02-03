package com.messagebird.objects;

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

    public Hlr() {
    }

    @Override
    public String toString() {
        return "Hlr{" +
                "id='" + id + '\'' +
                ", href='" + href + '\'' +
                ", msisdn=" + msisdn +
                ", network='" + network + '\'' +
                ", reference='" + reference + '\'' +
                ", details='" + details + '\'' +
                ", status='" + status + '\'' +
                ", createdDatetime=" + createdDatetime +
                ", statusDatetime=" + statusDatetime +
                '}';
    }

    /**
     * An unique random ID which is created on the MessageBird platform and is returned upon creation of the object.
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * The URL of the created object.
     * @return
     */
    public String getHref() {
        return href;
    }

    /**
     * The telephone number.
     * @return
     */
    public BigInteger getMsisdn() {
        return msisdn;
    }

    /**
     * The MCCMNC code of the network provider
     * @return
     */
    public String getNetwork() {
        return network;
    }

    /**
     * A client reference
     * @return
     */
    public String getReference() {
        return reference;
    }

    /**
     * The status of the msisdns. Possible values: sent, absent, active, unknown, and failed
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * The date and time of the creation of the message in RFC3339 format (Y-m-d\TH:i:sP)
     * @return
     */
    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    /**
     * The datum time of the last status in RFC3339 format (Y-m-d\TH:i:sP)
     * @return
     */
    public Date getStatusDatetime() {
        return statusDatetime;
    }

    public HlrDetails getDetails() {
        return details;
    }
}

