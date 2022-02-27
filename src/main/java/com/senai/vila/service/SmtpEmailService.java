package com.senai.vila.service;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author gustavo
 *
 *	https://www.google.com/settings/security/lesssecureapps
 * 	https://accounts.google.com/b/0/DisplayUnlockCaptcha
 *
 *
 */
public class SmtpEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    private MailSender mailSender;

    public SmtpEmailService(@Value("${spring.mail.username}") String sender,
                            @Value("${spring.mail.password}") String password,
                            MailSender mailSender) {
        super(sender, password);
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(SimpleMailMessage message) {
        LOG.info("Enviando email...");
        mailSender.send(message);
        LOG.info("Email enviado");
    }

    @SneakyThrows
    @Override
    public void sendEmail(MimeMessage message) {
        Transport.send(message);
    }

    @Override
    public SimpleMailMessage prepareEmailTargetSubjectMessage(String target, String subject, String message) {
        return super.prepareEmailTargetSubjectMessage(target, subject, message);
    }

    @SneakyThrows
    @Override
    public MimeMessage prepareEmailMimeMessage(String target, String subject, String message, MimeMultipart mimeMultipart) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.EnableSSL.enable","true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.socketFactory.port", "465");


        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        InternetAddress iaSender = new InternetAddress(sender);
        InternetAddress iaRecipient = new InternetAddress(target);

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setSender(iaSender);
        mimeMessage.setSubject(subject);
        mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
        mimeMessage.setContent(mimeMultipart);

        return mimeMessage;
    }

    @SneakyThrows
    public MimeMultipart preparePdfToEmail(String fileName, String message) {
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText(message);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = baos.toByteArray();

        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
        MimeBodyPart pdfBodyPart = new MimeBodyPart();
        pdfBodyPart.setDataHandler(new javax.activation.DataHandler(dataSource));
        pdfBodyPart.setFileName(fileName + ".pdf");

        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(textBodyPart);
        String path = Paths.get(fileName + ".pdf").toAbsolutePath().toString();
        pdfBodyPart.attachFile(path, "application/pdf", "base64");
        mimeMultipart.addBodyPart(pdfBodyPart);

        return mimeMultipart;
    }

}
