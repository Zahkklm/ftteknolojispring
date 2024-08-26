package com.patika.notificationservice.service;

import com.patika.notificationservice.dto.request.NotificationRequest;

public interface NotificationStrategy {
    void sendNotification(NotificationRequest notificationRequest);
}
