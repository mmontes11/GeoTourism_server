package com.mmontes.rest.request;

public class ConfigBBoxRequest {

    String bbox;

    public ConfigBBoxRequest() {
    }

    public ConfigBBoxRequest(String bbox) {
        this.bbox = bbox;
    }

    public String getBbox() {
        return bbox;
    }

    public void setBbox(String bbox) {
        this.bbox = bbox;
    }
}
