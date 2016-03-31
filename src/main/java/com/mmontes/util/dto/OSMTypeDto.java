package com.mmontes.util.dto;

public class OSMTypeDto {

    private Long id;
    private IDnameDto key;
    private IDnameDto value;
    private Long tipTypeId;

    public OSMTypeDto() {
    }

    public OSMTypeDto(Long id, IDnameDto key, IDnameDto value, Long tipTypeId) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.tipTypeId = tipTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IDnameDto getKey() {
        return key;
    }

    public void setKey(IDnameDto key) {
        this.key = key;
    }

    public IDnameDto getValue() {
        return value;
    }

    public void setValue(IDnameDto value) {
        this.value = value;
    }

    public Long getTipTypeId() {
        return tipTypeId;
    }

    public void setTipTypeId(Long tipTypeId) {
        this.tipTypeId = tipTypeId;
    }
}
