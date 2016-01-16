package com.mmontes.util.dto;

public class FeatureSearchDto {

    private Long id;
    private String geom;
    private String icon;

    public FeatureSearchDto() {
    }

    public FeatureSearchDto(Long id, String geom, String icon) {
        this.id = id;
        this.geom = geom;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
