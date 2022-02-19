package com.senai.vila.model.entity;

import com.senai.vila.model.dto.HabitanteDto;
import com.senai.vila.model.dto.TiposHabitanteDto;
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "habitantes_tipos_habitante",
            joinColumns = @JoinColumn(name = "habitante_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_habitante_id")
    )
    private List<TiposHabitante> tiposHabitante = new ArrayList<>();


    public Habitante() {
    }

    public Habitante(String email, String senha, List<TiposHabitante> tiposUsuario) {
        this.email = email;
        this.senha = senha;
        this.tiposHabitante = tiposUsuario;
    }

    public Habitante(String email, String senha, Set<String> tiposUsuario) {
        this.email = email;
        this.senha = senha;
        this.tiposHabitante = tiposUsuario.stream().map(TiposHabitante::new).collect(Collectors.toList());
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
        return true;
    }

    public void setPassword(String senha) {
        this.senha = senha;
    }

    public List<TiposHabitante> getTiposHabitante() {
        return tiposHabitante;
    }

    public void setTiposHabitante(List<TiposHabitante> tiposHabitante) {
        this.tiposHabitante = tiposHabitante;
    }

    public HabitanteDto converterEmDto() {
        List<TiposHabitanteDto> tiposHabitanteDto =
                this.tiposHabitante.stream().map(habitante -> {
                    return new TiposHabitanteDto(habitante.getNome());
                }).collect(Collectors.toList());
        return new HabitanteDto(this.email, tiposHabitanteDto);
    }
}
