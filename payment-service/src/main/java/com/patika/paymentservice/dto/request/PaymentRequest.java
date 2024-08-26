package com.patika.paymentservice.dto.request;

import com.patika.paymentservice.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequest {

    private BigDecimal amount;
    private String email;
    private PaymentType paymentType;
}
