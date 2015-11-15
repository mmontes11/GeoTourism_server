package com.mmontes.rest.request;

public class UserRequest {

    private String accessToken;
    private Long userID;

    public UserRequest() {
    }

    public UserRequest(String accessToken, Long userID) {
        this.accessToken = accessToken;
        this.userID = userID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
