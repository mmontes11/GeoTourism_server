package com.mmontes.util.dto;

public class OSMTypeDto {

    private Long id;
    private String key;
    private String value;
    private TIPtypeDto tipType;

    public OSMTypeDto() {
    }

    public OSMTypeDto(Long id, String key, String value, TIPtypeDto tipType) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.tipType = tipType;
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

    public TIPtypeDto getTipType() {
        return tipType;
    }

    public void setTipType(TIPtypeDto tipType) {
        this.tipType = tipType;
    }
}
