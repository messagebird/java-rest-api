package com.messagebird.objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * The type of message.
 *
 * Created by rvt on 1/7/15.
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum MsgType {
    sms("sms"),
    mms("mms"),
    binary("binary"),
    premium("premium"),
    flash("flash");

    @Getter
    final String value;

}
