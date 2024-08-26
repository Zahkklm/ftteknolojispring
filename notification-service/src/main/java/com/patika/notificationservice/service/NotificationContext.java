package com.patika.notificationservice.service;


import com.patika.notificationservice.dto.request.NotificationRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationContext {

    private final Map<String, NotificationStrategy> strategies;

    public NotificationContext(Map<String, NotificationStrategy> strategies) {
        this.strategies = strategies;
    }

    public void executeStrategy(NotificationRequest notificationRequest) {
        String strategyString = notificationRequest.getType().toString() + "NotificationStrategy";
        NotificationStrategy strategy = strategies.get(strategyString);
        if (strategy != null) {
            strategy.sendNotification(notificationRequest);
        } else {
            throw new IllegalArgumentException("Desteklenmeyen bildirim türü: " + notificationRequest.getType().toString());
        }
    }
}
