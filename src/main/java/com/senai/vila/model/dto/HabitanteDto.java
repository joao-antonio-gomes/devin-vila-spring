package com.senai.vila.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitanteDto {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private List<TiposHabitanteDto> tiposHabitante;

    public HabitanteDto(String email, List<TiposHabitanteDto> tiposHabitante) {
        this.email = email;
        this.tiposHabitante = tiposHabitante;
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

    public List<TiposHabitanteDto> getTiposHabitante() {
        return tiposHabitante;
    }

    public void setTiposHabitante(List<TiposHabitanteDto> tiposHabitante) {
        this.tiposHabitante = tiposHabitante;
    }
}
