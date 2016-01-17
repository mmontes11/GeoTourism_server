package com.mmontes.util.dto;

public class TIPMinDto {

    private Long id;
    private String name;
    private String googleMapsurl;
    private String icon;

    public TIPMinDto() {
    }

    public TIPMinDto(Long id, String name, String googleMapsurl, String icon) {
        this.id = id;
        this.name = name;
        this.googleMapsurl = googleMapsurl;
        this.icon = icon;
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

    public String getGoogleMapsurl() {
        return googleMapsurl;
    }

    public void setGoogleMapsurl(String googleMapsurl) {
        this.googleMapsurl = googleMapsurl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}