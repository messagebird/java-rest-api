package com.messagebird.objects.voicecalls;

public class RecordingResponseList {
    private RecordingResponse[] data;

    public RecordingResponseList(RecordingResponse[] data) {
        this.data = data;
    }

    public RecordingResponseList() {
    }

    public RecordingResponse[] getData() {
        return data;
    }

    public void setData(RecordingResponse[] data) {
        this.data = data;
    }
}
