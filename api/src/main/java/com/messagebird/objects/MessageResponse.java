package com.messagebird.objects;

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
public class MessageResponse implements MessageResponseBase, Serializable {

    private static final long serialVersionUID = 6363132950790389653L;

    private String id;
    private String href;
    private String direction;
    private MsgType type;
    private String originator;
    private String body;
    private String reference;
    private Integer validity;
    private Integer gateway;
    private Map<String, Object> typeDetails;
    private DataCodingType datacoding = DataCodingType.unicode;
    private MClassType mclass;
    private Date scheduledDatetime;
    private Date createdDatetime;
    private Recipients recipients;

    public MessageResponse() {
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "id='" + id + '\'' +
                ", href='" + href + '\'' +
                ", direction='" + direction + '\'' +
                ", type=" + type +
                ", originator='" + originator + '\'' +
                ", body='" + body + '\'' +
                ", reference='" + reference + '\'' +
                ", validity=" + validity +
                ", gateway=" + gateway +
                ", typeDetails=" + typeDetails +
                ", datacoding=" + datacoding +
                ", mclass=" + mclass +
                ", scheduledDatetime=" + scheduledDatetime +
                ", createdDatetime=" + createdDatetime +
                ", recipients=" + recipients +
                '}';
    }

    /**
     * An unique random ID which is created on the MessageBird platform and is returned upon creation of the object.
     *
     * @return String
     */
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getHref() {
        return href;
    }

    /**
     * Tells you if the message is sent or received.
     * mt: mobile terminated (sent to mobile)
     * mo: mobile originated (received from mobile)
     *
     * @return String
     */
    public String getDirection() {
        return direction;
    }

    /**
     * The type of message. Values can be: sms, binary, premium, or flash
     *
     * @return MsgType
     */
    public MsgType getType() {
        return type;
    }

    /**
     * The sender of the message. This can be a telephone number (including country code) or an alphanumeric string. In case of an alphanumeric string, the maximum length is 11 characters.
     *
     * @return String
     */
    public String getOriginator() {
        return originator;
    }

    @Override
    public String getBody() {
        return body;
    }


    @Override
    public String getReference() {
        return reference;
    }

    /**
     * Your reference for this message
     *
     * @param reference
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * The amount of seconds that the message is valid. If a message is not delivered within this time, the message will be discarded.
     *
     * @return Integer
     */
    public Integer getValidity() {
        return validity;
    }

    /**
     * The SMS route that is used to send the message.
     *
     * @return Integer
     */
    public Integer getGateway() {
        return gateway;
    }

    /**
     * The datacoding used, can be plain or unicode
     *
     * @return DataCodingType
     */
    public DataCodingType getDatacoding() {
        return datacoding;
    }

    /**
     * Indicated the message type. 1 is a normal message, 0 is a flash message.
     *
     * @return MClassType
     */
    public MClassType getMclass() {
        return mclass;
    }

    /**
     * The scheduled date and time of the message
     *
     * @return Date
     */
    public Date getScheduledDatetime() {
        return scheduledDatetime;
    }

    /**
     * The date and time of the creation of the message
     *
     * @return Date
     */
    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    @Override
    public Recipients getRecipients() {
        return recipients;
    }

    /**
     * Return type details object
     *
     * @return Map<String   ,       Object>
     */
    public Map<String, Object> getTypeDetails() {
        return typeDetails;
    }


    /**
     * Recipient status
     */
    static public class Recipients implements Serializable {

        private static final long serialVersionUID = 547164972757802213L;

        private Integer totalSentCount;
        private Integer totalDeliveredCount;
        private Integer totalDeliveryFailedCount;
        private List<Items> items;

        public Recipients() {
        }

        @Override
        public String toString() {
            return "Recipients{" +
                    "totalSentCount=" + totalSentCount +
                    ", totalDeliveredCount=" + totalDeliveredCount +
                    ", totalDeliveryFailedCount=" + totalDeliveryFailedCount +
                    ", items=" + items +
                    '}';
        }

        /**
         * The count of recipients that have the message pending (status sent, and buffered).
         *
         * @return Integer
         */
        public Integer getTotalSentCount() {
            return totalSentCount;
        }

        /**
         * The count of recipients where the message is delivered (status delivered).
         *
         * @return Integer
         */
        public Integer getTotalDeliveredCount() {
            return totalDeliveredCount;
        }

        /**
         * The count of recipients where the delivery has failed (status delivery_failed).
         *
         * @return Integer
         */
        public Integer getTotalDeliveryFailedCount() {
            return totalDeliveryFailedCount;
        }

        /**
         * An array of recipient hashes
         *
         * @return List<Items>
         */
        public List<Items> getItems() {
            return items;
        }
    }

    /**
     * Response recipient items
     */
    static public class Items implements Serializable {

        private static final long serialVersionUID = -4104837036540050532L;

        private BigInteger recipient;
        private String status;
        private Date statusDatetime;

        public Items() {
        }

        @Override
        public String toString() {
            return "Items{" +
                    "recipient=" + recipient +
                    ", status='" + status + '\'' +
                    ", statusDatetime=" + statusDatetime +
                    "}";
        }

        /**
         * The msisdn of the recipient
         *
         * @return BigInteger
         */
        public BigInteger getRecipient() {
            return recipient;
        }

        /**
         * The status of the message sent to the recipient. Possible values: scheduled, sent, buffered, delivered, and delivery_failed
         *
         * @return String
         */
        public String getStatus() {
            return status;
        }

        /**
         * The datum time of the last status
         *
         * @return Date
         */
        public Date getStatusDatetime() {
            return statusDatetime;
        }

    }
}

