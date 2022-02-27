package com.senai.vila.consumer;

import com.senai.vila.enums.RabbitMqEnums;
import com.senai.vila.model.dto.FinancialReportDto;
import com.senai.vila.service.EmailService;
import com.senai.vila.service.PdfService;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class EmailConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMqEnums.QUEUE_MESSAGE_EMAIL_FINANCIAL_REPORT_ERROR)
    public void receiveFinancialReportError(String email) {
        System.out.println("Erro no relatório financeiro");
        System.out.println("Enviando email para: " + email);
        String subject = "Notificação de Geração de Relatório Financeiro";
        String message = "A geração do seu relatório financeiro falhou após um máximo de 3 tentativas mal sucedidas.";
        SimpleMailMessage emailMessage = emailService.prepareEmailTargetSubjectMessage(email, subject, message);
        emailService.sendEmail(emailMessage);
    }

    @SneakyThrows
    @RabbitListener(queues = RabbitMqEnums.QUEUE_MESSAGE_EMAIL_FINANCIAL_REPORT_SUCCESS)
    public void receiveFinancialReportSuccess(FinancialReportDto financialReportDto) {
        String fileName = PdfService.createPdfFinancialReport(financialReportDto);
        String email = financialReportDto.getEmail();
        String subject = "Relatório Financeiro " + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String message = "Segue relatório em anexo.";

        MimeMultipart mimeMultipart = emailService.preparePdfToEmail(fileName, message);
        MimeMessage mimeMail = emailService.prepareEmailMimeMessage(email, subject, message, mimeMultipart);
        emailService.sendEmail(mimeMail);

        System.out.println("Relatório financeiro criado com sucesso");
        System.out.println("Enviando email para: " + email);

        new File(fileName + ".pdf").delete();
    }
}
