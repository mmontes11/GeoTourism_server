package com.mmontes.util.dto;

public class IDnameDto {
    private Long id;
    private String name;

    public IDnameDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public IDnameDto() {
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
