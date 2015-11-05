package com.mmontes.rest.request;

public class TIPRequest {

    private Long type;
    private String name;
    private String description;
    private String photoUrl;
    private String infoUrl;
    private String geometry;

    public TIPRequest() {
    }

    public TIPRequest(Long type, String name, String description, String photoUrl,String infoUrl, String geometry) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.infoUrl = infoUrl;
        this.geometry = geometry;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return "TIPRequest{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", infoUrl='" + infoUrl + '\'' +
                ", geometry='" + geometry + '\'' +
                '}';
    }
}
