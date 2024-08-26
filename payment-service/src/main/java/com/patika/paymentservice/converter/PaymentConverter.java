package com.patika.paymentservice.converter;

import com.patika.paymentservice.dto.response.PaymentResponse;
import com.patika.paymentservice.model.Payment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentConverter {
    public static PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .amount(payment.getAmount())
                .createdDateTime(payment.getCreatedDateTime())
                .email(payment.getEmail())
                .paymentStatus(payment.getPaymentStatus())
                .paymentType(payment.getPaymentType())
                .build();
    }

    public static List<PaymentResponse> toResponse(List<Payment> payments) {
        return payments.stream()
                .map(PaymentConverter::toResponse)
                .toList();
    }
}
