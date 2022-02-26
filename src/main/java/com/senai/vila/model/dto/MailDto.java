package com.senai.vila.model.dto;

import java.io.Serializable;

public class MailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
