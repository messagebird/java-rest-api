package com.messagebird.objects;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by faizan on 01/02/16.
 */
@Getter
@ToString
public class HlrDetails {
    String status_desc;
    String imsi;
    String country_iso;
    String country_name;
    String location_msc;
    String location_iso;
    int     ported;
    int     roaming;
}
