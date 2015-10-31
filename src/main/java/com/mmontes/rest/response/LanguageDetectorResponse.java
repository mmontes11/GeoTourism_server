package com.mmontes.rest.response;

public class LanguageDetectorResponse {

    private String language;

    public LanguageDetectorResponse(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
