package com.mmontes.util.dto;

public class CityDto {
    private Long id;
    private String name;

    public CityDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityDto() {
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
}
