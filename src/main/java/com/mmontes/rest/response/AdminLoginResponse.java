package com.mmontes.rest.response;

public class AdminLoginResponse {

    public String token;

    public AdminLoginResponse() {
    }

    public AdminLoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
