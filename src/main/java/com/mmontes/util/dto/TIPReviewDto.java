package com.mmontes.util.dto;

/**
 * Created by Martin on 07/04/2016.
 */
public class TIPReviewDto {

    private Long id;
    private String typeIcon;
    private String name;
    private String geom;
    private String cityName;

    public TIPReviewDto() {
    }

    public TIPReviewDto(Long id, String typeIcon, String name, String geom, String cityName) {
        this.id = id;
        this.typeIcon = typeIcon;
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

    public String getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(String typeIcon) {
        this.typeIcon = typeIcon;
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
