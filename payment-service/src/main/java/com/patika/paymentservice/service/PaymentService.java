package com.patika.paymentservice.service;

import com.patika.paymentservice.converter.PaymentConverter;
import com.patika.paymentservice.dto.request.PaymentRequest;
import com.patika.paymentservice.dto.response.PaymentResponse;
import com.patika.paymentservice.model.enums.PaymentStatus;
import com.patika.paymentservice.repository.PaymentRepository;
import com.patika.paymentservice.model.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentResponse createPayment(PaymentRequest request) {

        Payment payment = Payment.builder()
                .paymentStatus(PaymentStatus.PAID)
                .amount(request.getAmount())
                .email(request.getEmail())
                .createdDateTime(LocalDateTime.now())
                .paymentType(request.getPaymentType())
                .build();

        paymentRepository.save(payment);

        return PaymentConverter.toResponse(payment);
    }

    public List<PaymentResponse> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        return PaymentConverter.toResponse(payments);
    }
}
