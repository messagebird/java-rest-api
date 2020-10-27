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
public class TranscriptionResponse implements Serializable {

    private static final long serialVersionUID = -25064223639161201L;
    private List<Transcription> data;

    @JsonProperty("_links")
    private Map<String, String> links;
    private Pagination pagination;
}
