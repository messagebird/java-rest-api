package com.messagebird.objects;

/**
 * Contains writable values for the Groups API.
 */
public class GroupRequest {

    private String name;

    public GroupRequest(String name) {
        this.name = name;
    }

    public GroupRequest() {
        //
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GroupRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
