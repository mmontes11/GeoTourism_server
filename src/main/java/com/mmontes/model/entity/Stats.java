package com.mmontes.model.entity;


public class Stats {

    private Double latitude;
    private Double longitude;
    private Double intensity;

    public Stats() {
    }

    public Stats(Double latitude, Double longitude, Double intensity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.intensity = intensity;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getIntensity() {
        return intensity;
    }

    public void setIntensity(Double intensity) {
        this.intensity = intensity;
    }
}

