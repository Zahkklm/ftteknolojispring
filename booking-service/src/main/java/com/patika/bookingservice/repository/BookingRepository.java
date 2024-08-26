package com.patika.bookingservice.repository;

import com.patika.bookingservice.dto.response.BookingResponse;
import com.patika.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>, BookingRepositoryCustom, JpaSpecificationExecutor<Booking> {

    Optional<Booking> findByEmail(String email);
    Optional<Booking> findByBookingId(Long bookingId);
    Optional<List<Booking>> findBookingsByEmail(String email);


}
