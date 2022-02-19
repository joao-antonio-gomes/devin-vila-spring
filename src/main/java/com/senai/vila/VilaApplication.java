package com.senai.vila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class VilaApplication {

    public static void main(String[] args) {
        SpringApplication.run(VilaApplication.class, args);
    }

}
