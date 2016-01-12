package com.messagebird.objects;

import java.io.Serializable;

/**
 * Created by faizan on 09/12/15.
 */
public class Messages implements Serializable {
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String toString() {
        return "Messages{" +
                "href=" + this.href +
                "}";
    }
}
