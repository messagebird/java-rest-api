package com.messagebird.objects.voicecalls;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class WebhookResponseData implements Serializable {

    private static final long serialVersionUID = 7840085698939378254L;
    private List<WebhookResponse> data;
    private Map<String, String> _links;

    public List<WebhookResponse> getData() {
        return data;
    }

    public void setData(List<WebhookResponse> data) {
        this.data = data;
    }

    public Map<String, String> get_links() {
        return _links;
    }

    public void set_links(Map<String, String> _links) {
        this._links = _links;
    }

    @Override
    public String toString() {
        return "WebhookResponseData{" +
                "data=" + data +
                ", _links=" + _links +
                '}';
    }
}
