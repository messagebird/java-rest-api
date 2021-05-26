package com.messagebird.objects;

public class FileUploadResponse {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FileUploadResponse{" +
                "id='" + id + '\'' +
                '}';
    }
}
