package com.mmontes.util.dto;

import java.util.ArrayList;
import java.util.List;

public class ConfigDto {

    private String bbox;
    private List<OSMTypeDto> osmTypes = new ArrayList<>();

    public ConfigDto() {
    }

    public ConfigDto(String bbox, List<OSMTypeDto> osmTypes) {
        this.bbox = bbox;
        this.osmTypes = osmTypes;
    }

    public String getBbox() {
        return bbox;
    }

    public void setBbox(String bbox) {
        this.bbox = bbox;
    }

    public List<OSMTypeDto> getOsmTypes() {
        return osmTypes;
    }

    public void setOsmTypes(List<OSMTypeDto> osmTypes) {
        this.osmTypes = osmTypes;
    }
}
