package com.patika.bookingservice.exception;

import com.patika.bookingservice.dto.response.GenericResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public GenericResponse handleUserException(UserException userException){
        return GenericResponse.failed(userException.getMessage());
    }

}
