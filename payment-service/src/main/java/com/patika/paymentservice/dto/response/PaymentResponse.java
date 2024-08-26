package com.patika.paymentservice.dto.response;

import com.patika.paymentservice.model.enums.PaymentStatus;
import com.patika.paymentservice.model.enums.PaymentType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentResponse {

    private BigDecimal amount;
    private LocalDateTime createdDateTime;
    private PaymentStatus paymentStatus;
    private String email;
    private PaymentType paymentType;

}
