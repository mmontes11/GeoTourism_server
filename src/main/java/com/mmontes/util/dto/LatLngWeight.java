package com.mmontes.util.dto;

public class LatLngWeight {

    private Double lat;
    private Double lng;
    private Double weight;

    public LatLngWeight(Double lat, Double lng, Double weight) {
        this.lat = lat;
        this.lng = lng;
        this.weight = weight;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
