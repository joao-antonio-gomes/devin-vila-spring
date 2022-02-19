package com.senai.vila.model.entity;

import com.senai.vila.model.dto.RolesDto;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    public Roles() {

    }

    public Roles(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Roles(String name) {
        this.name = name;
    }

    public Roles(Roles role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    public RolesDto converterEmDto() {
        return new RolesDto(this.name);
    }
}
