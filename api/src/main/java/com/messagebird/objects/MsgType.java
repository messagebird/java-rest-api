package com.messagebird.objects;

/**
 * The type of message.
 *
 * Created by rvt on 1/7/15.
 */
public enum MsgType {
    sms("sms"),
    mms("mms"),
    binary("binary"),
    premium("premium"),
    flash("flash");

    final String value;

    MsgType(String type) {
        this.value = type;
    }

    @Override
    public String toString() {
        return "MsgType{" +
                "value='" + value + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }

}
