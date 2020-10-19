package com.messagebird.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by faizan on 09/12/15.
 */
@Getter
@Setter
@ToString
public class Verify implements Serializable {

    private String id;
    private String href;
    private String recipient;
    private Messages messages;
    private String status;
    private Date createdDatetime;
    private Date validUntilDatetime;
}
