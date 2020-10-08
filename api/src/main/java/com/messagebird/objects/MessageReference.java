package com.messagebird.objects;

public class MessageReference {

    private String href;
    private int totalCount;
    private String lastMessageId;

    public String getHREF() {
        return href;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setHREF(String href) {
        this.href = href;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getLastMessageId() {
        return lastMessageId;
    }

    @Override
    public String toString() {
        return "MessageReference{" +
                "href='" + href + '\'' +
                ", totalCount=" + totalCount +
                ", lastMessageId='" + lastMessageId + '\'' +
                '}';
    }
}
