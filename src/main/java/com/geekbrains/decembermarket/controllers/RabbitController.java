package com.geekbrains.decembermarket.controllers;


import com.geekbrains.decembermarket.utils.rabbitmq.SimpleMessageReceiver;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class RabbitController {
    public static final String EXCHANGE_FOR_PROCESSING_TASK = "TasksExchanger"; //исходящие задания
    public static final String QUEUE_WITH_PROCESSING_TASK_RESULTS = "TaskResultQueue"; //очередь с результатами

    private RabbitTemplate rabbitTemplate;
    private SimpleMessageReceiver receiver;


    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }

    @GetMapping("/rabbit/{message}")
    public String sendMessage(@PathVariable String message) {
        rabbitTemplate.convertAndSend(EXCHANGE_FOR_PROCESSING_TASK, null, "Task from Server: " + message);
        return null;
    }

    @Bean
    public SimpleMessageListenerContainer containerForTopic(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_WITH_PROCESSING_TASK_RESULTS);
        container.setMessageListener(listenerAdapter);
        return container;
    }


    @Bean
    public MessageListenerAdapter listenerAdapter(SimpleMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}