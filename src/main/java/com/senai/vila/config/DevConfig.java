package com.senai.vila.config;

import com.senai.vila.service.EmailService;
import com.senai.vila.service.SmtpEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;

@Configuration
@Profile("dev")
public class DevConfig {

    private String sender;
    private String password;
    public MailSender mailSender;

    public DevConfig(@Value("${default.sender}") String sender,
                     @Value("${spring.mail.password}") String password,
                     MailSender mailSender) {
        this.sender = sender;
        this.password = password;
        this.mailSender = mailSender;
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService(sender, password, mailSender);
    }
}