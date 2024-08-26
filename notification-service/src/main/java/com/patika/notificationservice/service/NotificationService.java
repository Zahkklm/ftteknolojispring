package com.patika.notificationservice.service;

import com.patika.notificationservice.dto.request.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationContext notificationContext;

    public NotificationService(NotificationContext notificationContext) {
        this.notificationContext = notificationContext;
    }

    public void sendNotification(NotificationRequest notificationRequest) {
        notificationContext.executeStrategy(notificationRequest);
    }
}
