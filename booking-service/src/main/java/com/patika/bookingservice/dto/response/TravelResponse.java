package com.patika.bookingservice.dto.response;

import com.patika.bookingservice.model.Booking;
import com.patika.bookingservice.model.enums.StatusType;
import com.patika.bookingservice.model.enums.TravelType;
import com.patika.bookingservice.model.enums.UserType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelResponse {

    private Set<Booking> bookings;
    private List<UserResponse> userDTO;
    private TravelType travelType;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String location;
    private String destination;
}
