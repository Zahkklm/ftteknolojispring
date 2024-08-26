package com.patika.notificationservice.producer;

import com.patika.notificationservice.config.RabbitMQConfig;
import com.patika.notificationservice.dto.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final AmqpTemplate rabbitTemplate;

    public void send(NotificationRequest notificationRequest) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                notificationRequest
        );
    }
}
