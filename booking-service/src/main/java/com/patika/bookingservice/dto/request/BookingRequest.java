package com.patika.bookingservice.dto.request;

import com.patika.bookingservice.model.enums.StatusType;
import lombok.*;
import com.patika.bookingservice.model.enums.UserType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {

    private String email;
    private String firstName;
    private String lastName;

    private Long bookingId;
    private Long travelId;

    private UserType userType;
    private StatusType statusType;

    private LocalDateTime bookingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String location;
    private String destination;
}
