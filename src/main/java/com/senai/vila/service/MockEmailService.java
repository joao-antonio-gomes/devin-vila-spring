package com.senai.vila.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MockEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    public MockEmailService(String sender, String password) {
        super(sender, password);
    }

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Simulando envio de email...");
        LOG.info(msg.toString());
        LOG.info("Email enviado");
    }

    @Override
    public SimpleMailMessage prepareEmailTargetSubjectMessage(String target, String subject, String message) {
        return null;
    }

    @Override
    public MimeMessage prepareEmailMimeMessage(String target, String subject, String message, MimeMultipart mimeMultipart) {
        return null;
    }

    @Override
    public void sendNewEmail(SimpleMailMessage message) {

    }
}

