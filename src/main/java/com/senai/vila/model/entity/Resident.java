package com.senai.vila.model.entity;

import com.senai.vila.model.dto.ResidentDto;
import com.senai.vila.model.dto.RolesDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "residents")
public class Resident implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String email;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "residents_roles",
            joinColumns = @JoinColumn(name = "resident_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Roles> roles = new ArrayList<>();


    public Resident() {
    }

    public Resident(String email, String senha, List<Roles> tiposUsuario) {
        this.email = email;
        this.password = senha;
        this.roles = tiposUsuario;
    }

    public Resident(String email, String senha, Set<String> tiposUsuario) {
        this.email = email;
        this.password = senha;
        this.roles = tiposUsuario.stream().map(Roles::new).collect(Collectors.toList());
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
        return this.password;
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
        this.password = senha;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public ResidentDto convertToDto() {
        List<RolesDto> rolesDto =
                this.roles.stream().map(habitante -> {
                    return new RolesDto(habitante.getName());
                }).collect(Collectors.toList());
        return new ResidentDto(this.email, rolesDto);
    }
}
