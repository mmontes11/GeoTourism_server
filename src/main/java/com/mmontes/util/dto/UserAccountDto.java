package com.mmontes.util.dto;

public class UserAccountDto {

    private Long facebookUserId;
    private String facebookProfileUrl;
    private String facebookProfilePhotoUrl;
    private String name;

    public UserAccountDto() {
    }

    public UserAccountDto(Long facebookUserId, String facebookProfileUrl, String facebookProfilePhotoUrl, String name) {
        this.facebookUserId = facebookUserId;
        this.facebookProfileUrl = facebookProfileUrl;
        this.facebookProfilePhotoUrl = facebookProfilePhotoUrl;
        this.name = name;
    }

    public Long getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(Long facebookUserId) {
        this.facebookUserId = facebookUserId;
    }

    public String getFacebookProfileUrl() {
        return facebookProfileUrl;
    }

    public void setFacebookProfileUrl(String facebookProfileUrl) {
        this.facebookProfileUrl = facebookProfileUrl;
    }

    public String getFacebookProfilePhotoUrl() {
        return facebookProfilePhotoUrl;
    }

    public void setFacebookProfilePhotoUrl(String facebookProfilePhotoUrl) {
        this.facebookProfilePhotoUrl = facebookProfilePhotoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
