package com.senai.vila.model.dto;

public class TiposHabitanteDto {
    private String nome;

    public TiposHabitanteDto(String name) {
        this.nome = name;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
