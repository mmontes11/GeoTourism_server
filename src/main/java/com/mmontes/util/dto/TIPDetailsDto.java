package com.mmontes.util.dto;


public class TIPDetailsDto {

    private Long id;
    private Long type;
    private String name;
    private String description;
    private String geom;
    private String address;
    private String photoUrl;
    private String infoUrl;
    private String googleMapsUrl;

    public TIPDetailsDto() {
    }

    public TIPDetailsDto(Long id, Long type, String name, String description, String geom, String address, String photoUrl, String inforUrl, String googleMapsUrl) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.geom = geom;
        this.address = address;
        this.photoUrl = photoUrl;
        this.infoUrl = inforUrl;
        this.googleMapsUrl = googleMapsUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
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

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getGoogleMapsUrl() {
        return googleMapsUrl;
    }

    public void setGoogleMapsUrl(String googleMapsUrl) {
        this.googleMapsUrl = googleMapsUrl;
    }
}
