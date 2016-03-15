package com.mmontes.rest.request;

public class TIPtypeRequest {

    private String name;
    private String icon;

    public TIPtypeRequest() {
    }

    public TIPtypeRequest(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
