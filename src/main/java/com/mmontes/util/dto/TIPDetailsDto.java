package com.mmontes.util.dto;


import java.util.List;

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
    private Double averageRate;
    private Double myRate;
    private List<UserAccountDto> favouritedBy;
    private Boolean myFavourite;
    private List<CommentDto> comments;

    public TIPDetailsDto() {
    }

    public TIPDetailsDto(Long id, Long type, String name, String description, String geom, String address, String photoUrl, String infoUrl,
                         String googleMapsUrl, Double averageRate, Double myRate, List<UserAccountDto> favouritedBy, Boolean myFavourite,
                         List<CommentDto> comments) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.geom = geom;
        this.address = address;
        this.photoUrl = photoUrl;
        this.infoUrl = infoUrl;
        this.googleMapsUrl = googleMapsUrl;
        this.averageRate = averageRate;
        this.myRate = myRate;
        this.favouritedBy = favouritedBy;
        this.myFavourite = myFavourite;
        this.comments = comments;
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

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }

    public Double getMyRate() {
        return myRate;
    }

    public void setMyRate(Double myRate) {
        this.myRate = myRate;
    }

    public List<UserAccountDto> getFavouritedBy() {
        return favouritedBy;
    }

    public void setFavouritedBy(List<UserAccountDto> favouritedBy) {
        this.favouritedBy = favouritedBy;
    }

    public Boolean getMyFavourite() {
        return myFavourite;
    }

    public void setMyFavourite(Boolean myFavourite) {
        this.myFavourite = myFavourite;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
