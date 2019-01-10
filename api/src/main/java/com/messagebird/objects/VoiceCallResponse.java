package com.messagebird.objects;

import java.io.Serializable;
import java.util.List;

public class VoiceCallResponse implements Serializable {

    private static final long serialVersionUID = -3429781513863789117L;

    private List<VoiceCallData> data;


    public List<VoiceCallData> getData() {
        return data;
    }

    public void setData(List<VoiceCallData> data) {
        this.data = data;
    }
}
