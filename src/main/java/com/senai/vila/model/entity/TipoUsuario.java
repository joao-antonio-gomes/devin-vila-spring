package com.senai.vila.model.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "tipo_usuario")
public class TipoUsuario implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nome;

    public TipoUsuario() {

    }

    public TipoUsuario(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public TipoUsuario(String nome) {
        this.nome = nome;
    }

    public TipoUsuario(TipoUsuario tipoUsuario) {
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
}
