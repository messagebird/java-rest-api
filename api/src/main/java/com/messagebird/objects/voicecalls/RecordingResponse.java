package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordingResponse implements Serializable {

    private static final long serialVersionUID = -3645909436942694671L;

    private List<Recording> data;
    @JsonProperty("_links")
    private Map<String, String> links;
    private Pagination pagination;

    public void calculate(int a1){
        System.out.println(a1);
    }
}
