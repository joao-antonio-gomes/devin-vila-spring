package com.senai.vila.controller.service;

import com.senai.vila.model.dto.ResidentDto;
import com.senai.vila.model.dto.RolesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SmtpEmailServiceTest {

    private SmtpEmailService smtpEmailService;
    private String sender = "joao@email.com";
    private MailSender mailSender;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        this.mailSender = Mockito.mock(MailSender.class);
        this.emailService = Mockito.mock(EmailService.class);
        this.smtpEmailService = new SmtpEmailService(sender, mailSender);
    }

    @Test
    public void testPrepareNewPasswordEmail() {
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.now(), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        String newPassword = "123456789";
        SimpleMailMessage simpleMailMessage = smtpEmailService.prepareNewPasswordEmail(residentDto, newPassword);
        Date systemDate = new Date(System.currentTimeMillis());
        assertTrue(Objects.equals(simpleMailMessage.getTo()[0], residentDto.getEmail()));
        assertTrue(Objects.equals(simpleMailMessage.getSubject(), "Solicitação de nova senha"));
        assertTrue(Objects.equals(simpleMailMessage.getText(), "Nova senha: " + newPassword));
    }
}
