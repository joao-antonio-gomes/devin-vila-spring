package com.senai.vila.exception;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.util.ErrorHandler;

public class QueueHandleException implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        String queueName = ((ListenerExecutionFailedException) t).getFailedMessage().getMessageProperties().getConsumerQueue();
        System.out.println("Queue: " + queueName + " erro ao processar mensagem");

        String message = new String(((ListenerExecutionFailedException) t).getFailedMessage().getBody());
        System.out.println(message);

        System.out.println(t.getCause().getMessage());

        throw new AmqpRejectAndDontRequeueException("Não deve retornar para a fila!");
    }
}
