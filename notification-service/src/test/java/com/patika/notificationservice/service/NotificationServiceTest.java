package com.patika.notificationservice.service;

import com.patika.notificationservice.dto.request.NotificationRequest;
import com.patika.notificationservice.dto.request.enums.NotificationType;
import com.patika.notificationservice.service.NotificationContext;
import com.patika.notificationservice.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationContext notificationContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNotification_Success() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("test-recipient");
        notificationRequest.setMessage("test-message");
        notificationRequest.setType(NotificationType.email);

        // When
        notificationService.sendNotification(notificationRequest);

        // Then
        verify(notificationContext, times(1)).executeStrategy(notificationRequest);
    }

    @Test
    void testSendNotification_WithDifferentNotificationType() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("test-recipient");
        notificationRequest.setMessage("test-message");
        notificationRequest.setType(NotificationType.sms);

        // When
        notificationService.sendNotification(notificationRequest);

        // Then
        verify(notificationContext, times(1)).executeStrategy(notificationRequest);
    }

    @Test
    void testSendNotification_NullRequest() {
        // Given
        NotificationRequest notificationRequest = null;

        // When
        notificationService.sendNotification(notificationRequest);

        // Then
        verify(notificationContext, times(1)).executeStrategy(notificationRequest);
    }
}
