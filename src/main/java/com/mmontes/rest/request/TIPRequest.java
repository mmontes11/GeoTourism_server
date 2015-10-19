package com.mmontes.rest.request;

public class TIPRequest {

    String type;
    String name;
    String description;
    String photoUrl;
    String photoContent;
    String photoName;
    String infoUrl;
    String geometry;

    public TIPRequest() {
    }

    public TIPRequest(String type, String name, String description, String photoUrl, String photoContent, String photoName, String infoUrl, String geometry) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.photoContent = photoContent;
        this.photoName = photoName;
        this.infoUrl = infoUrl;
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getPhotoContent() {
        return photoContent;
    }

    public void setPhotoContent(String photoContent) {
        this.photoContent = photoContent;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
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
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", photoContent='" + photoContent + '\'' +
                ", photoName='" + photoName + '\'' +
                ", infoUrl='" + infoUrl + '\'' +
                ", geom='" + geometry + '\'' +
                '}';
    }
}
