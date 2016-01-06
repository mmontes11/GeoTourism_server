package com.mmontes.rest.request;

import java.util.List;

public class RoutePatchRequest {

    private String name;
    private String description;
    private String travelMode;
    private List<Long> tipIds;

    public RoutePatchRequest() {
    }

    public RoutePatchRequest(String name, String description, String travelMode, List<Long> tipIds) {
        this.name = name;
        this.description = description;
        this.travelMode = travelMode;
        this.tipIds = tipIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public List<Long> getTipIds() {
        return tipIds;
    }

    public void setTipIds(List<Long> tipIds) {
        this.tipIds = tipIds;
    }
}
