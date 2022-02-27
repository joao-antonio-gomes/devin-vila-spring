package com.senai.vila.consumer;

import com.senai.vila.enums.RabbitMqEnums;
import com.senai.vila.model.dto.FinancialReportDto;
import com.senai.vila.service.FinancialReportService;
import com.senai.vila.service.SmtpEmailService;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialReportConsumer {

    private final FinancialReportService financialReportService;
    private final SmtpEmailService emailService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public FinancialReportConsumer(FinancialReportService financialReportService, SmtpEmailService emailService) {
        this.financialReportService = financialReportService;
        this.emailService = emailService;
    }

    @SneakyThrows
    @RabbitListener(queues = RabbitMqEnums.QUEUE_FINANCIAL_REPORT)
    public void receive(FinancialReportDto financialReportDto) {
        //Descomentar linha abaixo e requisitar 6x o relatório irá gerar 2 erros.
        //Thread.sleep(1200);

        if (financialReportDto.getId() == null) {
            throw new IllegalArgumentException("Id não pode ser nulo");
        }
        if (financialReportDto.getBudgetRemaining() == null) {
            throw new IllegalArgumentException("Saldo remanescente não pode ser nulo");
        }
        if (financialReportDto.getMostExpensiveResident() == null) {
            throw new IllegalArgumentException("Habitante mais caro não pode ser nulo");
        }
        rabbitTemplate.convertAndSend(RabbitMqEnums.EXCHANGE_MESSAGE_EMAIL, RabbitMqEnums.QUEUE_MESSAGE_EMAIL_FINANCIAL_REPORT_SUCCESS, financialReportDto);
    }

    @RabbitListener(queues = RabbitMqEnums.QUEUE_DEAD_LETTER_FINANCIAL_REPORT)
    public void receiveDeadLetter(FinancialReportDto financialReportDto) {
        rabbitTemplate.convertAndSend(RabbitMqEnums.EXCHANGE_MESSAGE_EMAIL, RabbitMqEnums.QUEUE_MESSAGE_EMAIL_FINANCIAL_REPORT_ERROR, financialReportDto.getEmail());
    }
}
