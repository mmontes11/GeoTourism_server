package com.mmontes.util.dto;

import java.util.List;

public class TIPtypeOSMDto {

    private Long id;
    private String name;
    private String icon;
    private List<OSMTypeDto> osmTypes;

    public TIPtypeOSMDto() {
    }

    public TIPtypeOSMDto(Long id, String name, String icon, List<OSMTypeDto> osmTypes) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.osmTypes = osmTypes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<OSMTypeDto> getOsmTypes() {
        return osmTypes;
    }

    public void setOsmTypes(List<OSMTypeDto> osmTypes) {
        this.osmTypes = osmTypes;
    }
}
