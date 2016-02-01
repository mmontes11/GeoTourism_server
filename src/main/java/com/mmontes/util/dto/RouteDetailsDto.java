package com.mmontes.util.dto;

import java.util.List;

public class RouteDetailsDto {

    private Long id;
    private String name;
    private String description;
    private String travelMode;
    private String geom;
    private String googleMapsUrl;
    private List<TIPRouteDto> tips;
    private UserAccountDto creator;

    public RouteDetailsDto() {
    }

    public RouteDetailsDto(Long id, String name, String description, String travelMode, String geom, String googleMapsUrl, List<TIPRouteDto> tips, UserAccountDto creator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.travelMode = travelMode;
        this.geom = geom;
        this.googleMapsUrl = googleMapsUrl;
        this.tips = tips;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public String getGoogleMapsUrl() {
        return googleMapsUrl;
    }

    public void setGoogleMapsUrl(String googleMapsUrl) {
        this.googleMapsUrl = googleMapsUrl;
    }

    public List<TIPRouteDto> getTips() {
        return tips;
    }

    public void setTips(List<TIPRouteDto> tips) {
        this.tips = tips;
    }

    public UserAccountDto getCreator() {
        return creator;
    }

    public void setCreator(UserAccountDto creator) {
        this.creator = creator;
    }
}
