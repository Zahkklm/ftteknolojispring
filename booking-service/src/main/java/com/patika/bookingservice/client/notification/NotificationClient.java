package com.patika.bookingservice.client.notification;

import com.patika.bookingservice.dto.request.NotificationRequest;
import com.patika.bookingservice.dto.response.GenericResponse;
import com.patika.bookingservice.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "notification-service", url = "http://localhost:8089/api/v1/notifications")
public interface NotificationClient {
    @PostMapping
    GenericResponse<List<UserResponse>> pushNotification(@RequestBody NotificationRequest notificationRequest);
}
