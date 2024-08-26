package com.patika.bookingservice.client.payment;

import com.patika.bookingservice.dto.response.GenericResponse;
import com.patika.bookingservice.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "payment-service", url = "http://localhost:9090/api/v1/payments")
public interface PaymentClient {

    @GetMapping("/{email}")
    GenericResponse<UserResponse> getUserByEmail(@PathVariable("email") String email);

    @GetMapping
    GenericResponse<List<UserResponse>> getAllUsers();
}
