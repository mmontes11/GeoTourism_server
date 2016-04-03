package com.mmontes.rest.request;

public class TIPtypeRequest {

    private String name;
    private String icon;
    private String osmKey;
    private String osmValue;

    public TIPtypeRequest() {
    }

    public TIPtypeRequest(String name, String icon, String osmKey, String osmValue) {
        this.name = name;
        this.icon = icon;
        this.osmKey = osmKey;
        this.osmValue = osmValue;
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

    public String getOsmKey() {
        return osmKey;
    }

    public void setOsmKey(String osmKey) {
        this.osmKey = osmKey;
    }

    public String getOsmValue() {
        return osmValue;
    }

    public void setOsmValue(String osmValue) {
        this.osmValue = osmValue;
    }
}
