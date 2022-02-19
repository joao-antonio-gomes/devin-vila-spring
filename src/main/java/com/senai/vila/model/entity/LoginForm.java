package com.senai.vila.model.entity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {
    private String email;
    private String password;

    public LoginForm() {

    }

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "email='" + email + '\'' +
                ", senha='" + password + '\'' +
                '}';
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }
}
