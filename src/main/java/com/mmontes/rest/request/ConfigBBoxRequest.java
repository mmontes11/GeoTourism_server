package com.mmontes.rest.request;

public class ConfigBBoxRequest {

    String geom;

    public ConfigBBoxRequest() {
    }

    public ConfigBBoxRequest(String geom) {
        this.geom = geom;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }
}
