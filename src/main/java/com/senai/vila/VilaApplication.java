package com.senai.vila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class VilaApplication {

    public static void main(String[] args) {
        SpringApplication.run(VilaApplication.class, args);
    }

}
