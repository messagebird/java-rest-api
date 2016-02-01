package com.messagebird.objects;

/**
 * Created by faizan on 01/02/16.
 */
public class HlrDetails {
    String status_desc;
    String imsi;
    String country_iso;
    String country_name;
    String location_msc;
    String location_iso;
    int     ported;
    int     roaming;


    @Override
    public String toString() {
        return "HlrDetails{" +
                "status_desc='" + status_desc + '\'' +
                ", imsi='" + imsi + '\'' +
                ", country_iso=" + country_iso +
                ", country_name='" + country_name + '\'' +
                ", location_msc='" + location_msc + '\'' +
                ", location_iso='" + location_iso + '\'' +
                ", ported=" + Integer.toString(ported) +
                ", roaming=" + Integer.toString(roaming) +
                '}';
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public String getImsi() {
        return imsi;
    }

    public String getCountry_iso() {
        return country_iso;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getLocation_msc() {
        return location_msc;
    }

    public String getLocation_iso() {
        return location_iso;
    }

    public int getPorted() {
        return ported;
    }

    public int getRoaming() {
        return roaming;
    }
}
