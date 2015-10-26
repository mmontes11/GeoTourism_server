package com.mmontes.util.dto;


public class TIPDetailsDto {

    private Long id;
    private String type;
    private String name;
    private String description;
    private String geom;
    private String address;
    private String photoUrl;
    private String infoUrl;
    private String googleMapsUrl;
    private String city;
    private String region;
    private String country;

    public TIPDetailsDto() {
    }

    public TIPDetailsDto(Long id, String type, String name, String description, String geom, String address, String photoUrl, String inforUrl, String googleMapsUrl, String city, String region, String country) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.geom = geom;
        this.address = address;
        this.photoUrl = photoUrl;
        this.infoUrl = inforUrl;
        this.googleMapsUrl = googleMapsUrl;
        this.city = city;
        this.region = region;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
