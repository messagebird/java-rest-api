package com.messagebird.objects.conversations;

/**
 * Coordinates that point to a location.
 */
public class ConversationContentLocation {

    private double latitude, longitude;

    public ConversationContentLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ConversationContentLocation() {
        //
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "ConversationContentLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
