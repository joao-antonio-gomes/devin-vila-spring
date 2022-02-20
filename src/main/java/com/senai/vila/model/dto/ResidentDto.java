package com.senai.vila.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResidentDto {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    private String password;
    private List<RolesDto> roles;
    private Double rent;
    private LocalDate birthDate;
    private String cpf;
    private String firstName;
    private String lastName;
    public ResidentDto() {

    }
    public ResidentDto(String email, List<RolesDto> roles) {
        this.email = email;
        this.roles = roles;
    }

    //Construtor utilizado para registro de usuário
    public ResidentDto(String firstName, String lastName, String cpf, LocalDate birthDate, Double rent, String email,
                       String password, List<String> roles) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.rent = rent;
        this.email = email;
        this.password = bCryptPasswordEncoder.encode(password);
        this.roles = roles.stream().map(role -> new RolesDto(role)).collect(Collectors.toList());
    }

    public ResidentDto(String firstName, String lastName, String cpf, LocalDate birthDate, Double rent, String email,
                       List<RolesDto> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.rent = rent;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
