package com.messagebird.objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * The datacoding used. Specifies plain or unicode
 *
 * Created by rvt on 1/7/15.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum DataCodingType {
    plain("plain"),
    unicode("unicode"),
    auto("auto");

    @Getter
    final String value;

    @Override
    public String toString() {
        return value;
    }
}
