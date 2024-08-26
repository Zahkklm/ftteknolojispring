package com.patika.bookingservice.converter;

import com.patika.bookingservice.dto.response.BookingResponse;
import com.patika.bookingservice.dto.response.TravelResponse;
import com.patika.bookingservice.model.Booking;
import com.patika.bookingservice.model.Travel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelConverter {

    public static TravelResponse toResponse(Travel travel) {
        return TravelResponse.builder()
                .travelType(travel.getTravelType())
                .startTime(travel.getStartTime())
                .endTime(travel.getEndTime())
                .location(travel.getLocation())
                .destination(travel.getDestination())
                .userDTO(travel.getUserDTO()) // empty List<> of userDTO
                .bookings(travel.getBookings()) // empty Set<> of Booking
                .build();
    }

    public static List<TravelResponse> toResponse(List<Travel> travels) {
        return travels.stream().map(TravelConverter::toResponse).toList();
    }
}
