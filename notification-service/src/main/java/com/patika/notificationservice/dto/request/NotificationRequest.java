package com.patika.notificationservice.dto.request;

import com.patika.notificationservice.dto.request.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {
    private String recipient;
    private String message;
    private NotificationType type;
}
