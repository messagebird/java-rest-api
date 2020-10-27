package com.messagebird.objects.voicecalls;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Webhook implements Serializable {

    private static final long serialVersionUID = 727746356185518354L;

    private String url;
    private String token;
}
