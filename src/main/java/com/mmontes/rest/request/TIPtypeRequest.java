package com.mmontes.rest.request;

public class TIPtypeRequest {

    private String name;
    private String icon;
    private String osmType;

    public TIPtypeRequest() {
    }

    public TIPtypeRequest(String name, String icon, String osmType) {
        this.name = name;
        this.icon = icon;
        this.osmType = osmType;
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

    public String getOsmType() {
        return osmType;
    }

    public void setOsmType(String osmType) {
        this.osmType = osmType;
    }
}
