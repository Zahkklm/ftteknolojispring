package com.patika.notificationservice.service;

import com.patika.notificationservice.dto.request.NotificationRequest;
import com.patika.notificationservice.dto.request.enums.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class NotificationListenerTest {

    @InjectMocks
    private NotificationListener notificationListener;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveMessage_Success() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("test-recipient");
        notificationRequest.setMessage("test-message");
        notificationRequest.setType(NotificationType.email);

        // When
        notificationListener.receiveMessage(notificationRequest);

        // Then
        verify(notificationService, times(1)).sendNotification(notificationRequest);
    }

    @Test
    void testReceiveMessage_WithDifferentNotificationType() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("test-recipient");
        notificationRequest.setMessage("test-message");
        notificationRequest.setType(NotificationType.sms);

        // When
        notificationListener.receiveMessage(notificationRequest);

        // Then
        verify(notificationService, times(1)).sendNotification(notificationRequest);
    }

    @Test
    void testReceiveMessage_NullRequest() {
        // Given
        NotificationRequest notificationRequest = null;

        // When
        notificationListener.receiveMessage(notificationRequest);

        // Then
        verify(notificationService, times(0)).sendNotification(notificationRequest);
    }
}
