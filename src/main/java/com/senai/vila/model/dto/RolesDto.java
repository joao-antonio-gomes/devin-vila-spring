package com.senai.vila.model.dto;

public class RolesDto {
    private String name;

    public RolesDto() {
    }

    public RolesDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
