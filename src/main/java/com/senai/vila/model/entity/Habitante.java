package com.senai.vila.model.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "habitantes")
public class Habitante implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String email;
    private String senha;
    private boolean ativo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "habitantes_tipo_usuario",
            joinColumns = @JoinColumn(name = "habitante_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_usuario_id")
    )
    private List<TipoUsuario> tiposUsuario = new ArrayList<>();


    public Habitante() {
    }

    public Habitante(String email, String senha, List<TipoUsuario> tiposUsuario) {
        this.email = email;
        this.senha = senha;
        this.tiposUsuario = tiposUsuario;
    }

    public Habitante(String email, String senha, Set<String> tiposUsuario) {
        this.email = email;
        this.senha = senha;
        this.tiposUsuario = tiposUsuario.stream().map(TipoUsuario::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }

    public void setPassword(String senha) {
        this.senha = senha;
    }

    public List<TipoUsuario> getTiposUsuario() {
        return tiposUsuario;
    }

    public void setTiposUsuario(List<TipoUsuario> tiposUsuario) {
        this.tiposUsuario = tiposUsuario;
    }
}
