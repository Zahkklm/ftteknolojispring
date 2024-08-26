package com.patika.bookingservice.dto.request;

import com.patika.bookingservice.dto.request.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {
    private String recipient;
    private String message;
    private NotificationType type;
}
