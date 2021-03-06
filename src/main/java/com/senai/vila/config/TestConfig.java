package com.senai.vila.config;

import com.senai.vila.service.EmailService;
import com.senai.vila.service.MockEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public EmailService emailService() {
        return new MockEmailService("Test Sender", "test");
    }
}

