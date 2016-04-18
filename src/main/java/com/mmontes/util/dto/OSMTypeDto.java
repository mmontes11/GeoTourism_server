package com.mmontes.util.dto;

public class OSMTypeDto {

    private Long id;
    private String key;
    private String value;
    private Long tipTypeID;

    public OSMTypeDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTipTypeID() {
        return tipTypeID;
    }

    public void setTipTypeID(Long tipTypeID) {
        this.tipTypeID = tipTypeID;
    }
}
