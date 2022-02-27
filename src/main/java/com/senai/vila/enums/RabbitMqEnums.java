package com.senai.vila.enums;

public enum RabbitMqEnums {
    ;
    public static final String QUEUE_FINANCIAL_REPORT = "queue.vila.financial_report";
    public static final String EXCHANGE_FINANCIAL_REPORT = "exchange.vila.financial_report";
    public static final String QUEUE_DEAD_LETTER_FINANCIAL_REPORT = "queue.vila.dead_letter.financial_report";
    public static final String EXCHANGE_DEAD_LETTER_FINANCIAL_REPORT = "exchange.vila.dead_letter.financial_report";
    public static final String QUEUE_MESSAGE_EMAIL_FINANCIAL_REPORT_ERROR = "queue.vila.message_email.financial_report.error";
    public static final String QUEUE_MESSAGE_EMAIL_FINANCIAL_REPORT_SUCCESS = "queue.vila.message_email.financial_report.success";
    public static final String EXCHANGE_MESSAGE_EMAIL = "exchange.vila.message_email";
}
