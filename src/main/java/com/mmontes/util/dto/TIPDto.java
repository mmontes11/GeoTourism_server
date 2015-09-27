package com.mmontes.util.dto;


import com.vividsolutions.jts.geom.Geometry;

public class TIPDto {

    private Long id;
    private String type;
    private String typeName;
    private String name;
    private String description;
    private Geometry geom;
    private String address;
    private String photoUrl;
    private String inforUrl;
    private String googleMapsUrl;
    private String city;
    private String region;
    private String country;

    public TIPDto() {
    }

    public TIPDto(Long id, String type, String name, String description, Geometry geom, String address, String photoUrl, String inforUrl, String googleMapsUrl, String city, String region, String country) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.geom = geom;
        this.address = address;
        this.photoUrl = photoUrl;
        this.inforUrl = inforUrl;
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

    public Geometry getGeom() {
        return geom;
    }

    public void setGeom(Geometry geom) {
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

    public String getInforUrl() {
        return inforUrl;
    }

    public void setInforUrl(String inforUrl) {
        this.inforUrl = inforUrl;
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
