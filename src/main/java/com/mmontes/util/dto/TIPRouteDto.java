package com.mmontes.util.dto;

public class TIPRouteDto {

    private Long id;
    private String name;
    private String googleMapsUrl;
    private String icon;
    private String geom;

    public TIPRouteDto() {
    }

    public TIPRouteDto(Long id, String name, String googleMapsUrl, String icon, String geom) {
        this.id = id;
        this.name = name;
        this.googleMapsUrl = googleMapsUrl;
        this.icon = icon;
        this.geom = geom;
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

    public String getGoogleMapsUrl() {
        return googleMapsUrl;
    }

    public void setGoogleMapsUrl(String googleMapsUrl) {
        this.googleMapsUrl = googleMapsUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }
}