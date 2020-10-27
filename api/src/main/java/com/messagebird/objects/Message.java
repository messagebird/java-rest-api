package com.messagebird.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to hold's a Voice message that can be send
 * <p/>
 * Created by rvt on 1/7/15.
 */
@ToString
public class Message implements MessageBase, Serializable {

    private static final long serialVersionUID = -2110297078875657480L;

    private static final String EMPTY = "";

    @Getter
    private String originator;
    private String body;
    private String recipients;

    @Getter
    private MsgType type;

    @Setter
    private String reference;

    @Getter
    private Integer validity;

    @Getter
    @Setter
    private Integer gateway;

    @Getter
    @Setter
    private Map<String, Object> typeDetails;

    @Getter
    @Setter
    private DataCodingType datacoding = DataCodingType.plain;

    @Getter
    private MClassType mclass;

    @Setter
    private Date scheduledDatetime;

    /**
     * Optional URL for the status report. If not set uses URL from account settings.
     */
    @Getter
    @Setter
    private URL reportUrl;

    public Message(String originator, String body, String recipients) {
        if (recipients == null || recipients.trim().length() == 0) {
            throw new IllegalArgumentException("Recipients must be specified");
        }
        if (body == null || body.trim().length() == 0) {
            throw new IllegalArgumentException("Body must be specified");
        }
        if (originator == null || originator.trim().length() == 0) {
            throw new IllegalArgumentException("Originator must be specified");
        }
        this.recipients = recipients.trim();
        this.originator = originator.trim();
        this.originator = this.originator.substring(0, Math.min(17, this.originator.length()));

        this.body = body.trim();
    }

    public Message(String originator, String body, List<BigInteger> recipients) {
        this(originator, body, receipientsAsCommaSeperated(recipients));
    }

    /**
     * Factory to create Binary SMS message
     *
     * @param originator
     * @param header
     * @param body
     * @param recipients
     * @return
     */
    static public Message createBinarySMS(String originator, String header, String body, String recipients) {
        Message msg = new Message(originator, body, recipients);
        final Map<String, Object> binarySMS = new LinkedHashMap<String, Object>(4);
        binarySMS.put("udh", header);
        msg.setTypeDetails(binarySMS);
        msg.setType(MsgType.binary);
        return msg;
    }

    /**
     * Factory to create Binary SMS message
     *
     * @param originator
     * @param header
     * @param body
     * @param recipients
     * @return
     */
    static public Message createBinarySMS(String originator, String header, String body, List<BigInteger> recipients) {
        return Message.createBinarySMS(originator, header, body, receipientsAsCommaSeperated(recipients));
    }

    /**
     * generates a string from  List<BigInteger>
     *
     * @param recipients
     * @return
     */
    protected static String receipientsAsCommaSeperated(final List<BigInteger> recipients) {
        // Note: I didn't opt for using apache commons to reduce the number of includes
        // Let me know if you want to have this changed...
        if (recipients == null) {
            return null;
        }
        if (recipients.size() == 0) {
            return EMPTY;
        }
        final StringBuilder sb = new StringBuilder(recipients.size() * 10);
        for (final BigInteger s : recipients) {
            sb.append(s.toString()).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getRecipients() {
        return recipients;
    }


    /**
     * The type of message. Values can be: sms, binary, premium, or flash.
     * Default: sms (set by MessageBird and will not be send)
     *
     * @param type Message Type
     */
    public void setType(MsgType type) {
        this.type = type;
        if (type == MsgType.flash) {
            this.mclass = MClassType.flash;
        } else {
            this.mclass = MClassType.normal;
        }
    }

    @Override
    public String getReference() {
        return reference;
    }

    /**
     * The amount of seconds that the message is valid. If a message is not delivered within this time, the message will be discarded.
     *
     * @param validity
     */
    public void setValidity(Integer validity) {
        if (validity < 0) {
            throw new IllegalArgumentException("validity must be >= 0");
        }
        this.validity = validity;
    }

    @Override
    public Date getScheduledDatetime() {
        return scheduledDatetime;
    }

    /**
     * Setup premium SMS type
     *
     * @param shortcode
     * @param keyword
     * @param tariff
     * @param mid
     */
    public void setPremiumSMS(Object shortcode, Object keyword, Object tariff, Object mid) {
        final Map<String, Object> premiumSMSConfig = new LinkedHashMap<String, Object>(4);
        premiumSMSConfig.put("shortcode", shortcode);
        premiumSMSConfig.put("keyword", keyword);
        premiumSMSConfig.put("tariff", tariff);
        premiumSMSConfig.put("mid", mid);
        this.typeDetails = premiumSMSConfig;
        this.type = MsgType.premium;
    }

}
