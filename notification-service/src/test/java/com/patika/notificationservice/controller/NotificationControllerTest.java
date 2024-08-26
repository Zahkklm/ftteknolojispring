package com.patika.notificationservice.controller;

import com.patika.notificationservice.dto.request.NotificationRequest;
import com.patika.notificationservice.dto.response.GenericResponse;
import com.patika.notificationservice.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNotification_Success() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        // Set valid data for notificationRequest

        doNothing().when(notificationService).sendNotification(notificationRequest);

        // When
        GenericResponse<String> response = notificationController.sendNotification(notificationRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Bildirim yollandı!", response.getData());
    }

    @Test
    void testSendNotification_Exception() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        // Set valid data for notificationRequest

        doThrow(new RuntimeException("Service exception")).when(notificationService).sendNotification(notificationRequest);

        // When/Then
        assertThrows(RuntimeException.class, () -> {
            notificationController.sendNotification(notificationRequest);
        });
    }

    @Test
    void testSendNotification_InvalidRequest() {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        // Set invalid data for notificationRequest if necessary

        // When/Then
        // Here you can check validation or other custom exception handling logic.
    }
}
