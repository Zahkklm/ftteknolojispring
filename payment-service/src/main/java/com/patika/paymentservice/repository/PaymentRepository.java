package com.patika.paymentservice.repository;

import com.patika.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


}
