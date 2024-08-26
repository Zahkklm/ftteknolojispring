package com.patika.notificationservice.service;

import com.patika.notificationservice.dto.request.NotificationRequest;
import com.patika.notificationservice.dto.request.enums.NotificationType;
import com.patika.notificationservice.exception.NotificationException;
import com.patika.notificationservice.model.Notification;
import com.patika.notificationservice.producer.NotificationProducer;
import com.patika.notificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SmsNotificationStrategyTest {

    @InjectMocks
    private SmsNotificationStrategy smsNotificationStrategy;

    @Mock
    private NotificationProducer notificationProducer;

    @Mock
    private NotificationRepository notificationRepository;

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
        notificationRequest.setType(NotificationType.sms);

        doNothing().when(notificationProducer).send(notificationRequest);
        when(notificationRepository.save(any(Notification.class))).thenReturn(new Notification());

        // When
        smsNotificationStrategy.sendNotification(notificationRequest);

        // Then
        verify(notificationProducer, times(1)).send(notificationRequest);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testSendNotification_NotificationProducerException() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("test-recipient");
        notificationRequest.setMessage("test-message");
        notificationRequest.setType(NotificationType.sms);

        doThrow(new RuntimeException("Producer exception")).when(notificationProducer).send(notificationRequest);

        // When/Then
        assertThrows(NotificationException.class, () -> {
            smsNotificationStrategy.sendNotification(notificationRequest);
        });

        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void testSendNotification_RepositorySaveFailure() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("test-recipient");
        notificationRequest.setMessage("test-message");
        notificationRequest.setType(NotificationType.sms);

        doNothing().when(notificationProducer).send(notificationRequest);
        doThrow(new RuntimeException("Repository exception")).when(notificationRepository).save(any(Notification.class));

        // When/Then
        assertThrows(RuntimeException.class, () -> {
            smsNotificationStrategy.sendNotification(notificationRequest);
        });

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }
}
