package com.messagebird.objects.voicecalls;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RecordingResponse implements Serializable {

    private static final long serialVersionUID = -3645909436942694671L;

    private List<Recording> data;
    private Map<String, String> _links;
    private Pagination pagination;

    public List<Recording> getData() {
        return data;
    }

    public void setData(List<Recording> data) {
        this.data = data;
    }

    public Map<String, String> get_links() {
        return _links;
    }

    public void set_links(Map<String, String> _links) {
        this._links = _links;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "RecordingResponse{" +
                "data=" + data +
                ", _links=" + _links +
                ", pagination=" + pagination +
                '}';
    }
}
