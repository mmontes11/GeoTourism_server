package com.mmontes.util.dto;

public class OSMTypeDto {

    private String key;
    private String value;
    private Long tipTypeId;

    public OSMTypeDto() {
    }

    public OSMTypeDto(String key, String value, Long tipTypeId) {
        this.key = key;
        this.value = value;
        this.tipTypeId = tipTypeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTipTypeId() {
        return tipTypeId;
    }

    public void setTipTypeId(Long tipTypeId) {
        this.tipTypeId = tipTypeId;
    }
}
