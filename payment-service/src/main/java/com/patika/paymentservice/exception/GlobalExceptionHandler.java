package com.patika.paymentservice.exception;

import com.patika.paymentservice.dto.response.GenericResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public GenericResponse handleUserException(UserException userException){
        return GenericResponse.failed(userException.getMessage());
    }

}
