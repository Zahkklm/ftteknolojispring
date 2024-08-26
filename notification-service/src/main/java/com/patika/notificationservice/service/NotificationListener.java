package com.patika.notificationservice.service;

import com.patika.notificationservice.config.RabbitMQConfig;
import com.patika.notificationservice.dto.request.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationListener {

    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(NotificationRequest notificationRequest) {
        System.out.println(notificationRequest.getType() +
                " - Bildirim alındı: " + notificationRequest.getMessage()
                + " Alıcı: " + notificationRequest.getRecipient());
    }
}

