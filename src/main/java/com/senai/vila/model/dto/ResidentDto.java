package com.senai.vila.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResidentDto {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private List<RolesDto> roles;

    public ResidentDto(String email, List<RolesDto> roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RolesDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesDto> roles) {
        this.roles = roles;
    }
}
