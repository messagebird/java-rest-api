package com.messagebird.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by faizan on 09/12/15.
 */
@Getter
@Setter
@ToString
public class Messages implements Serializable {
    private String href;
}
