package com.patika.notificationservice.service;

import com.patika.notificationservice.dto.request.NotificationRequest;
import com.patika.notificationservice.dto.request.enums.NotificationType;
import com.patika.notificationservice.service.NotificationContext;
import com.patika.notificationservice.service.NotificationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class NotificationContextTest {

    @InjectMocks
    private NotificationContext notificationContext;

    @Mock
    private Map<String, NotificationStrategy> strategies;

    @Mock
    private NotificationStrategy emailNotificationStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteStrategy_ValidNotificationType() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("test-recipient");
        notificationRequest.setMessage("test-message");
        notificationRequest.setType(NotificationType.email);

        when(strategies.get("emailNotificationStrategy")).thenReturn(emailNotificationStrategy);

        // When
        notificationContext.executeStrategy(notificationRequest);

        // Then
        verify(emailNotificationStrategy, times(1)).sendNotification(notificationRequest);
    }

    @Test
    void testExecuteStrategy_UnsupportedNotificationType() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("test-recipient");
        notificationRequest.setMessage("test-message");
        notificationRequest.setType(NotificationType.push);

        when(strategies.get("pushNotificationStrategy")).thenReturn(null);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> {
            notificationContext.executeStrategy(notificationRequest);
        });
    }

    @Test
    void testExecuteStrategy_NullNotificationType() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("test-recipient");
        notificationRequest.setMessage("test-message");
        notificationRequest.setType(null);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> {
            notificationContext.executeStrategy(notificationRequest);
        });

        verify(strategies, never()).get(anyString());
    }
}
