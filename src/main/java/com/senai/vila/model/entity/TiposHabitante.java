package com.senai.vila.model.entity;

import com.senai.vila.model.dto.TiposHabitanteDto;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "tipos_habitante")
public class TiposHabitante implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nome;

    public TiposHabitante() {

    }

    public TiposHabitante(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public TiposHabitante(String nome) {
        this.nome = nome;
    }

    public TiposHabitante(TiposHabitante tipoUsuario) {
        this.id = tipoUsuario.getId();
        this.nome = tipoUsuario.getNome();
    }

    public Long getId() {
        return id;
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return this.nome;
    }

    public TiposHabitanteDto converterEmDto() {
        return new TiposHabitanteDto(this.nome);
    }
}
