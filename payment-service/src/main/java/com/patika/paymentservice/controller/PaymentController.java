package com.patika.paymentservice.controller;

import com.patika.paymentservice.dto.request.PaymentRequest;
import com.patika.paymentservice.dto.response.PaymentResponse;
import com.patika.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse createPayment(@RequestBody PaymentRequest request){
        return paymentService.createPayment(request);
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments(){
        return paymentService.getAllPayments();
    }

}
