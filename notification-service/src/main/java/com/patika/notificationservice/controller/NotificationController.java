package com.patika.notificationservice.controller;


import com.patika.notificationservice.dto.request.NotificationRequest;
import com.patika.notificationservice.dto.response.GenericResponse;
import com.patika.notificationservice.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public GenericResponse<String> sendNotification(@RequestBody NotificationRequest notificationRequest) {
      notificationService.sendNotification(notificationRequest);
      return GenericResponse.success("Bildirim yollandı!", HttpStatus.OK);
    }
}
