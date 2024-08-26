package com.patika.paymentservice.controller;


import com.patika.paymentservice.converter.PaymentConverter;
import com.patika.paymentservice.dto.request.PaymentRequest;
import com.patika.paymentservice.dto.response.PaymentResponse;
import com.patika.paymentservice.model.Payment;
import com.patika.paymentservice.model.enums.PaymentStatus;
import com.patika.paymentservice.model.enums.PaymentType;
import com.patika.paymentservice.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.patika.paymentservice.repository.PaymentRepository;

public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void testCreatePayment_Success() throws Exception {
        // Given
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(BigDecimal.valueOf(100.0));
        paymentRequest.setEmail("test@example.com");
        paymentRequest.setPaymentType(PaymentType.CREDIT_CARD);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setAmount(BigDecimal.valueOf(100.0));
        paymentResponse.setEmail("test@example.com");
        paymentResponse.setPaymentType(PaymentType.CREDIT_CARD);
        paymentResponse.setPaymentStatus(PaymentStatus.PAID);

        when(paymentService.createPayment(any(PaymentRequest.class))).thenReturn(paymentResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":100.0,\"email\":\"test@example.com\",\"paymentType\":\"CREDIT_CARD\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"amount\":100.0,\"email\":\"test@example.com\",\"paymentType\":\"CREDIT_CARD\",\"paymentStatus\":\"PAID\"}"));
    }

    @Test
    void testGetAllPayments_Success() throws Exception {
        // Given
        PaymentResponse paymentResponse1 = new PaymentResponse();
        paymentResponse1.setAmount(BigDecimal.valueOf(100.0));
        paymentResponse1.setEmail("test1@example.com");
        paymentResponse1.setPaymentType(PaymentType.CREDIT_CARD);
        paymentResponse1.setPaymentStatus(PaymentStatus.PAID);

        PaymentResponse paymentResponse2 = new PaymentResponse();
        paymentResponse2.setAmount(BigDecimal.valueOf(200.0));
        paymentResponse2.setEmail("test2@example.com");
        paymentResponse2.setPaymentType(PaymentType.CREDIT);
        paymentResponse2.setPaymentStatus(PaymentStatus.PAID);

        List<PaymentResponse> paymentResponses = Arrays.asList(paymentResponse1, paymentResponse2);

        when(paymentService.getAllPayments()).thenReturn(paymentResponses);

        // When & Then
        mockMvc.perform(get("/api/v1/payments"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"amount\":100.0,\"email\":\"test1@example.com\",\"paymentType\":\"CREDIT_CARD\",\"paymentStatus\":\"PAID\"}," +
                        "{\"id\":2,\"amount\":200.0,\"email\":\"test2@example.com\",\"paymentType\":\"DEBIT_CARD\",\"paymentStatus\":\"PAID\"}]"));
    }

    @Test
    void testCreatePayment_InvalidRequest() {
        // Given
        PaymentRequest paymentRequest = new PaymentRequest();
        // PaymentRequest without required fields

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.createPayment(paymentRequest);
        });

        assertEquals("Required fields missing", exception.getMessage());
    }

    @Test
    void testCreatePayment_RepositoryException() {
        // Given
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(BigDecimal.valueOf(100.0));
        paymentRequest.setEmail("test@example.com");
        paymentRequest.setPaymentType(PaymentType.CREDIT_CARD);

        PaymentRepository paymentRepository = null;
        when(paymentRepository.save(any(Payment.class)))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.createPayment(paymentRequest);
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void testGetAllPayments_EmptyList() {
        PaymentRepository paymentRepository = null;
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());

        List<PaymentResponse> expectedResponses = new ArrayList<>();

        // When
        List<PaymentResponse> actualResponses = paymentService.getAllPayments();

        // Then
        assertEquals(expectedResponses, actualResponses);
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testCreatePayment_NullRequest() {
        // Given
        PaymentRequest paymentRequest = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            paymentService.createPayment(paymentRequest);
        });
    }

    @Test
    void testCreatePayment_MissingFields() {
        // Given
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(null); // Missing amount
        paymentRequest.setEmail("test@example.com");
        paymentRequest.setPaymentType(PaymentType.CREDIT_CARD);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.createPayment(paymentRequest);
        });
    }

    @Test
    void testCreatePayment_RepositoryThrowsException() {
        // Given
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(BigDecimal.valueOf(100.0));
        paymentRequest.setEmail("test@example.com");
        paymentRequest.setPaymentType(PaymentType.CREDIT_CARD);

        PaymentRepository paymentRepository = null;
        when(paymentRepository.save(any(Payment.class))).thenThrow(new RuntimeException("Database error"));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.createPayment(paymentRequest);
        });

        assertEquals("Database error", exception.getMessage());
    }


    @Test
    void testGetAllPayments_MultipleEntries() {
        // Given
        List<Payment> payments = List.of(
                Payment.builder()
                        .paymentStatus(PaymentStatus.PAID)
                        .amount(BigDecimal.valueOf(100.0))
                        .email("test1@example.com")
                        .createdDateTime(LocalDateTime.now())
                        .paymentType(PaymentType.CREDIT_CARD)
                        .build(),
                Payment.builder()
                        .paymentStatus(PaymentStatus.PAID)
                        .amount(BigDecimal.valueOf(200.0))
                        .email("test2@example.com")
                        .createdDateTime(LocalDateTime.now())
                        .paymentType(PaymentType.CREDIT)
                        .build()
        );

        PaymentRepository paymentRepository = null;
        when(paymentRepository.findAll()).thenReturn(payments);

        List<PaymentResponse> expectedResponses = PaymentConverter.toResponse(payments);

        // When
        List<PaymentResponse> actualResponses = paymentService.getAllPayments();

        // Then
        assertEquals(expectedResponses, actualResponses);
        verify(paymentRepository, times(1)).findAll();
    }


}


