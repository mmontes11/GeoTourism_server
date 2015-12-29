package com.mmontes.rest.request;


import java.util.List;

public class RouteRequest {

    private String name;
    private String description;
    private String travelMode;
    private List<String> lineStrings;
    private List<Long> tipIds;

    public RouteRequest() {
    }

    public RouteRequest(String name, String description, String travelMode, List<String> lineStrings, List<Long> tipIds) {
        this.name = name;
        this.description = description;
        this.travelMode = travelMode;
        this.lineStrings = lineStrings;
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

    public List<String> getLineStrings() {
        return lineStrings;
    }

    public void setLineStrings(List<String> lineStrings) {
        this.lineStrings = lineStrings;
    }

    public List<Long> getTipIds() {
        return tipIds;
    }

    public void setTipIds(List<Long> tipIds) {
        this.tipIds = tipIds;
    }
}
