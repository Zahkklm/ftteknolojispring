package com.patika.notificationservice.service;

import com.patika.notificationservice.dto.request.NotificationRequest;
import com.patika.notificationservice.dto.request.enums.NotificationType;
import com.patika.notificationservice.exception.NotificationException;
import com.patika.notificationservice.model.Notification;
import com.patika.notificationservice.producer.NotificationProducer;
import com.patika.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SmsNotificationStrategy implements NotificationStrategy {
    private final NotificationProducer notificationProducer;
    private final NotificationRepository notificationRepository;

    public SmsNotificationStrategy(NotificationProducer notificationProducer, NotificationRepository notificationRepository) {
        this.notificationProducer = notificationProducer;
        this.notificationRepository = notificationRepository;
    }
    @Override
    public void sendNotification(NotificationRequest notificationRequest) {
        System.out.println("SMS gönderilen kişi: " + notificationRequest.getRecipient() + " içerik: " + notificationRequest.getMessage());

        try {
            notificationProducer.send(notificationRequest);
        }catch (Exception exception){
            throw new NotificationException(exception.getMessage());
        }

        Notification notification = Notification.builder()
                .recipient(notificationRequest.getRecipient())
                .message(notificationRequest.getMessage())
                .createdDateTime(LocalDateTime.now())
                .type(NotificationType.sms)
                .build();

        notificationRepository.save(notification);
    }
}
