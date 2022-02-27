package com.senai.vila.service;

import com.senai.vila.model.dto.ResidentDto;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public interface EmailService {

    void sendEmail(SimpleMailMessage message);

    void sendEmail(MimeMessage message);

    void sendNewPassword(ResidentDto user, String newPassword);

    SimpleMailMessage prepareEmailTargetSubjectMessage(String target, String subject, String message);

    MimeMessage prepareEmailMimeMessage(String target, String subject, String message, MimeMultipart mimeMultipart);

    MimeMultipart preparePdfToEmail(String fileName, String message);
}
