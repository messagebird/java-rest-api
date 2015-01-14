package com.messagebird.objects;

/**
 * Link's class used only during List retreival and stored the first/previous etc data
 * Created by rvt on 1/8/15.
 */
public class Links {
    private String first;
    private String previous;
    private String next;
    private String last;

    public Links() {
    }

    @Override
    public String toString() {
        return "Links{" +
                "first='" + first + '\'' +
                ", previous='" + previous + '\'' +
                ", next='" + next + '\'' +
                ", last='" + last + '\'' +
                '}';
    }

    public String getFirst() {
        return first;
    }

    public String getPrevious() {
        return previous;
    }

    public String getNext() {
        return next;
    }

    public String getLast() {
        return last;
    }
}
