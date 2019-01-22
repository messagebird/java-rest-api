package com.messagebird.objects.voicecalls;

import java.io.Serializable;
import java.util.List;

public class TranscriptionResponse implements Serializable {

    private static final long serialVersionUID = -25064223639161201L;
    private List<Transcription> data;

    public List<Transcription> getData() {
        return data;
    }

    public void setData(List<Transcription> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TranscriptionResponse{" +
                "data=" + data +
                '}';
    }
}
