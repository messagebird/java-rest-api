package com.messagebird.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Link's class used only during List retreival and stored the first/previous etc data
 * Created by rvt on 1/8/15.
 */
@Getter
@ToString
@NoArgsConstructor
public class Links {
    private String first;
    private String previous;
    private String next;
    private String last;
}
