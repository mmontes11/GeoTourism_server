package com.mmontes.rest.request;

import com.mmontes.util.KeyValue;

import java.util.List;

public class TIPtypeRequest {
    private String name;
    private String icon;
    private List<KeyValue> osmTypes;

    public TIPtypeRequest() {
    }

    public TIPtypeRequest(String name, String icon, List<KeyValue> osmTypes) {
        this.name = name;
        this.icon = icon;
        this.osmTypes = osmTypes;
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

    public List<KeyValue> getOsmTypes() {
        return osmTypes;
    }

    public void setOsmTypes(List<KeyValue> osmTypes) {
        this.osmTypes = osmTypes;
    }
}
