package com.patika.notificationservice.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotificationException.class)
    public GenericResponse handleUserException(NotificationException notificationException){
        return GenericResponse.failed(notificationException.getMessage());
    }

}
