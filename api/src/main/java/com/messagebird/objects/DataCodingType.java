package com.messagebird.objects;

/**
 * The datacoding used. Specifies plain or unicode
 *
 * Created by rvt on 1/7/15.
 */
public enum DataCodingType {
    plain("plain"),
    unicode("unicode"),
    auto("auto");

    final String value;

    DataCodingType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DataCoding{" +
                "value='" + value + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }
}
