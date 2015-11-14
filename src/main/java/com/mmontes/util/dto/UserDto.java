package com.mmontes.util.dto;

public class UserDto {

    private Long facebookUserId;
    private String facebookProfilePhotoUrl;
    private String name;

    public UserDto() {
    }

    public UserDto(Long facebookUserId, String facebookProfilePhotoUrl, String name) {
        this.facebookUserId = facebookUserId;
        this.facebookProfilePhotoUrl = facebookProfilePhotoUrl;
        this.name = name;
    }

    public Long getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(Long facebookUserId) {
        this.facebookUserId = facebookUserId;
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
