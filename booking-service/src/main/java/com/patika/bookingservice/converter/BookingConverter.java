package com.patika.bookingservice.converter;

import com.patika.bookingservice.dto.response.BookingResponse;
import com.patika.bookingservice.model.Booking;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingConverter {

    public static BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .email(booking.getEmail())
                .firstName(booking.getFirstName())
                .userType(booking.getUserType())
                .statusType(booking.getStatusType())
                .bookingDate(booking.getBookingDate())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .location(booking.getLocation())
                .destination(booking.getDestination())
                .build();
    }

    public static List<BookingResponse> toResponse(List<Booking> bookings) {
        return bookings.stream().map(BookingConverter::toResponse).toList();
    }
}
