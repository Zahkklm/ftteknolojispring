package com.patika.bookingservice.controller;

import com.patika.bookingservice.dto.request.BookingRequest;
import com.patika.bookingservice.dto.request.TravelRequest;
import com.patika.bookingservice.dto.response.GenericResponse;
import com.patika.bookingservice.dto.response.BookingResponse;
import com.patika.bookingservice.dto.response.TravelResponse;
import com.patika.bookingservice.exception.ExceptionMessages;
import com.patika.bookingservice.model.enums.StatusType;
import com.patika.bookingservice.service.BookingService;
import com.patika.bookingservice.service.TravelService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public GenericResponse<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) {

        return GenericResponse.success(bookingService.createBooking(bookingRequest), HttpStatus.OK);
    }

    @PostMapping("/cancel") // cancel booking
    public GenericResponse<BookingResponse> cancelBooking(@RequestBody Long bookingId) {

        return GenericResponse.success(bookingService.cancelBooking(bookingId), HttpStatus.OK);
    }

    @GetMapping // for admin
    public GenericResponse<List<BookingResponse>> getAllBookings() {

        // if userRole is admin

        return GenericResponse.success(bookingService.getAllBookings(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public GenericResponse<List<BookingResponse>> getBookingsByEmail(@PathVariable String email) {

        return GenericResponse.success(bookingService.getBookingsByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public GenericResponse<BookingResponse> getBookingsByEmail(@PathVariable Long bookingId) {
        return GenericResponse.success(bookingService.getByBookingId(bookingId), HttpStatus.OK);
    }

}
