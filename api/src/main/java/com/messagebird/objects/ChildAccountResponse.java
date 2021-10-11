package com.messagebird.objects;

import java.io.Serializable;

public class ChildAccountResponse implements Serializable {
    private static final long serialVersionUID = -8605510461438669942L;

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChildAccountResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
