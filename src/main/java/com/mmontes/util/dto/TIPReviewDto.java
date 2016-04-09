package com.mmontes.util.dto;

public class TIPReviewDto {

    private Long id;
    private String icon;
    private String name;
    private String geom;
    private String cityName;

    public TIPReviewDto() {
    }

    public TIPReviewDto(Long id, String icon, String name, String geom, String cityName) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.geom = geom;
        this.cityName = cityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
