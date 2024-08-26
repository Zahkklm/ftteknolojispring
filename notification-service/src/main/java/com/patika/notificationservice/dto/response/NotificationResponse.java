package com.patika.notificationservice.dto.response;

import com.patika.notificationservice.dto.request.enums.NotificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationResponse {

    private LocalDateTime createdDateTime;

    private NotificationType type;

    private String recipient;
    private String message;

}
