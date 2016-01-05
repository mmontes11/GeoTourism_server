package com.mmontes.rest.request;

public class TIPPatchRequest {

    private Long type;
    private String name;
    private String description;
    private String infoUrl;
    private String address;
    private String photoUrl;

    public TIPPatchRequest() {
    }

    public TIPPatchRequest(Long type, String name, String description, String infoUrl, String address, String photoUrl) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.infoUrl = infoUrl;
        this.address = address;
        this.photoUrl = photoUrl;
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

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
