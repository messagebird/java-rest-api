package com.messagebird.objects;

public class ContactReference {

    private String href;
    private int totalCount;

    public String getHref() {
        return href;
    }

    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public String toString() {
        return "ContactReference{" +
                "href='" + href + '\'' +
                ", totalCount=" + totalCount +
                '}';
    }
}
