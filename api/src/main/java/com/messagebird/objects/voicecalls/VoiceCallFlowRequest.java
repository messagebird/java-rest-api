package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.messagebird.objects.VoiceStep;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Contains writable values for VoiceCallFlow objects.
 */
@Data
@NoArgsConstructor
public class VoiceCallFlowRequest {

    private String id;
    private String title;
    private boolean record;
    private List<VoiceStep> steps;
    
    @JsonProperty("default")
    private boolean defaultCall;

    public VoiceCallFlowRequest(String id)
    {
        this.id = id;
    }

}
