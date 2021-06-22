package com.messagebird.objects;

import java.io.Serializable;

/**
 * Created by leandro.pinto on 22/06/15.
 */
public class VerifyMessage implements Serializable {

    private String id;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "VerifyMessage {" + " " +
                "id=" + this.id + " " +
                "status=" + this.status + " " +
                "}";
    }
}
