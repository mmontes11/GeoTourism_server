package com.mmontes.util.dto;

import java.util.List;

public class RouteDetailsDto {

    private Long id;
    private String name;
    private String description;
    private String geom;
    private String googleMapsUrl;
    private List<TIPMinDto> tips;
    private UserAccountDto creator;

    public RouteDetailsDto() {
    }

    public RouteDetailsDto(Long id, String name, String description, String geom, String googleMapsUrl, List<TIPMinDto> tips, UserAccountDto creator) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public List<TIPMinDto> getTips() {
        return tips;
    }

    public void setTips(List<TIPMinDto> tips) {
        this.tips = tips;
    }

    public UserAccountDto getCreator() {
        return creator;
    }

    public void setCreator(UserAccountDto creator) {
        this.creator = creator;
    }
}
