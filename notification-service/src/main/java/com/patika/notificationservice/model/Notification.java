package com.patika.notificationservice.model;

import com.patika.notificationservice.dto.request.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdDateTime;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String recipient;
    private String message;

}
