package com.messagebird.objects;

import lombok.*;

/**
 * Contains writable values for the Groups API.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupRequest {
    @Getter
    @Setter
    private String name;
}
