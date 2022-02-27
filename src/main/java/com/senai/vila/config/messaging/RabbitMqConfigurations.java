package com.senai.vila.config.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.senai.vila.enums.RabbitMqEnums;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class RabbitMqConfigurations {

    @Autowired
    private AmqpAdmin amqpAdmin;

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter converter(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    public Queue createNewQueue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    public DirectExchange createNewDirectExchange(String exchangeName) {
        return new DirectExchange(exchangeName);
    }

    public Binding createBindingBetweenQueueAndDirectExchange(Queue queue, DirectExchange exchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    public void createQueueAndExchange(Queue queue, DirectExchange exchange) {
        Binding bind = createBindingBetweenQueueAndDirectExchange(queue, exchange);

        amqpAdmin.declareQueue(queue);

        amqpAdmin.declareExchange(exchange);

        amqpAdmin.declareBinding(bind);
    }

    @Bean
    public void createDeadLetterFinancialReport() {
        Queue queue = createNewQueue(RabbitMqEnums.QUEUE_DEAD_LETTER_FINANCIAL_REPORT);
        DirectExchange exchange = createNewDirectExchange(RabbitMqEnums.EXCHANGE_DEAD_LETTER_FINANCIAL_REPORT);
        createQueueAndExchange(queue, exchange);
    }

    @Bean
    public void createFinancialReportQueueAndExchange() {
        Queue queue = createNewQueue(RabbitMqEnums.QUEUE_FINANCIAL_REPORT);
        queue.addArgument("x-message-ttl", 500);
        queue.addArgument("x-dead-letter-exchange", RabbitMqEnums.EXCHANGE_DEAD_LETTER_FINANCIAL_REPORT);
        queue.addArgument("x-dead-letter-routing-key", RabbitMqEnums.QUEUE_DEAD_LETTER_FINANCIAL_REPORT);
        DirectExchange exchange = createNewDirectExchange(RabbitMqEnums.EXCHANGE_FINANCIAL_REPORT);
        createQueueAndExchange(queue, exchange);
    }

    @Bean
    public void createMessageQueueAndExchange() {
        Queue queueError = createNewQueue(RabbitMqEnums.QUEUE_MESSAGE_EMAIL_FINANCIAL_REPORT_ERROR);
        Queue queueSuccess = createNewQueue(RabbitMqEnums.QUEUE_MESSAGE_EMAIL_FINANCIAL_REPORT_SUCCESS);
        DirectExchange exchange = createNewDirectExchange(RabbitMqEnums.EXCHANGE_MESSAGE_EMAIL);
        createQueueAndExchange(queueError, exchange);
        createQueueAndExchange(queueSuccess, exchange);
    }
}
