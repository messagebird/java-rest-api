package com.messagebird.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This object represents a message response at MessageBird.com
 * <p/>
 * Created by rvt on 1/7/15.
 */
@NoArgsConstructor
@ToString
@Getter
public class MessageResponse implements MessageResponseBase, Serializable {

    private static final long serialVersionUID = 6363132950790389653L;

    private String id;
    private String href;
    private String direction;
    private MsgType type;
    private String originator;
    private String body;
    @Setter
    private String reference;
    private Integer validity;
    private Integer gateway;
    private Map<String, Object> typeDetails;
    private DataCodingType datacoding = DataCodingType.unicode;
    private MClassType mclass;
    private Date scheduledDatetime;
    private Date createdDatetime;
    private Recipients recipients;


    /**
     * Recipient status
     */
    @Getter
    @NoArgsConstructor
    @ToString
    static public class Recipients implements Serializable {

        private static final long serialVersionUID = 547164972757802213L;

        private Integer totalSentCount;
        private Integer totalDeliveredCount;
        private Integer totalDeliveryFailedCount;
        private List<Items> items;
    }

    /**
     * Response recipient items
     */
    @Getter
    @NoArgsConstructor
    @ToString
    static public class Items implements Serializable {

        private static final long serialVersionUID = -4104837036540050532L;

        private BigInteger recipient;
        private String status;
        private Date statusDatetime;
    }
}

