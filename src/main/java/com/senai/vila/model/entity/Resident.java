package com.senai.vila.model.entity;

import com.senai.vila.model.dto.ResidentDto;
import com.senai.vila.model.dto.RolesDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
    private Double rent;
    private LocalDate birthDate;
    private String cpf;
    @Column(name = "first_name")
    private String firsName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "residents_roles",
            joinColumns = @JoinColumn(name = "resident_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Roles> roles = new ArrayList<>();

    public Resident() {
    }

    public Resident(ResidentDto residentDto) {
        this.firsName = residentDto.getFirstName();
        this.lastName = residentDto.getLastName();
        this.cpf = residentDto.getCpf();
        this.birthDate = residentDto.getBirthDate();
        this.rent = residentDto.getRent();
        this.email = residentDto.getEmail();
        this.password = residentDto.getPassword();
        this.roles = residentDto.getRoles().stream().map(Roles::new).collect(Collectors.toList());
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

    public Double getRent() {
        return rent;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getCpf() {
        return cpf;
    }

    public String getFirsName() {
        return firsName;
    }

    public String getLastName() {
        return lastName;
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

    public void setPassword(String senha) {
        this.password = senha;
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

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public ResidentDto convertToDto() {
        List<RolesDto> rolesDto =
                this.roles.stream().map(resident -> {
                    return new RolesDto(resident.getName());
                }).collect(Collectors.toList());
        return new ResidentDto(this.firsName, this.lastName, this.cpf, this.birthDate, this.rent, this.email,
                rolesDto);
    }
}
