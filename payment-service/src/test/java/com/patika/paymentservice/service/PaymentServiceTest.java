package com.patika.paymentservice.service;

import com.patika.paymentservice.converter.PaymentConverter;
import com.patika.paymentservice.dto.request.PaymentRequest;
import com.patika.paymentservice.dto.response.PaymentResponse;
import com.patika.paymentservice.model.Payment;
import com.patika.paymentservice.model.enums.PaymentStatus;
import com.patika.paymentservice.model.enums.PaymentType;
import com.patika.paymentservice.repository.PaymentRepository;
import com.patika.paymentservice.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment_Success() {
        // Given
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(BigDecimal.valueOf(100.0));
        paymentRequest.setEmail("test@example.com");
        paymentRequest.setPaymentType(PaymentType.valueOf("CREDIT_CARD"));

        Payment payment = Payment.builder()
                .paymentStatus(PaymentStatus.PAID)
                .amount(paymentRequest.getAmount())
                .email(paymentRequest.getEmail())
                .createdDateTime(LocalDateTime.now())
                .paymentType(paymentRequest.getPaymentType())
                .build();

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponse expectedResponse = PaymentConverter.toResponse(payment);

        // When
        PaymentResponse actualResponse = paymentService.createPayment(paymentRequest);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testGetAllPayments_Success() {
        // Given
        List<Payment> payments = new ArrayList<>();
        Payment payment1 = Payment.builder()
                .paymentStatus(PaymentStatus.PAID)
                .amount(BigDecimal.valueOf(100.0))
                .email("test1@example.com")
                .createdDateTime(LocalDateTime.now())
                .paymentType(PaymentType.valueOf("CREDIT_CARD"))
                .build();
        Payment payment2 = Payment.builder()
                .paymentStatus(PaymentStatus.PAID)
                .amount(BigDecimal.valueOf(200.0))
                .email("test2@example.com")
                .createdDateTime(LocalDateTime.now())
                .paymentType(PaymentType.valueOf("DEBIT_CARD"))
                .build();
        payments.add(payment1);
        payments.add(payment2);

        when(paymentRepository.findAll()).thenReturn(payments);

        List<PaymentResponse> expectedResponses = PaymentConverter.toResponse(payments);

        // When
        List<PaymentResponse> actualResponses = paymentService.getAllPayments();

        // Then
        assertEquals(expectedResponses, actualResponses);
        verify(paymentRepository, times(1)).findAll();
    }
}

