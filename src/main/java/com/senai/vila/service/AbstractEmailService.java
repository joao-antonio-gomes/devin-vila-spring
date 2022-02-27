package com.senai.vila.service;

import com.senai.vila.model.dto.ResidentDto;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    protected String sender;
    protected String password;

    public AbstractEmailService(String sender, String password) {
        this.sender = sender;
        this.password = password;
    }

    @Override
    public void sendNewPassword(ResidentDto user, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(user, newPass);
        sendEmail(sm);
    }

    public SimpleMailMessage prepareNewPasswordEmail(ResidentDto user, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(user.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }

    @SneakyThrows
    public void sendEmail(MimeMessage message) {
        Transport.send(message);
    };

    @Override
    public SimpleMailMessage prepareEmailTargetSubjectMessage(String target, String subject, String message) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(target);
        sm.setFrom(sender);
        sm.setSubject(subject);
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(message);
        return sm;
    }

    public void sendNewEmail(SimpleMailMessage message) {
        sendEmail(message);
    }

    public MimeMultipart preparePdfToEmail(String fileName, String message) {
        return null;
    }
}
