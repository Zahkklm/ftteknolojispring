package com.patika.bookingservice.dto.response;

import com.patika.bookingservice.model.enums.UserType;
import com.patika.bookingservice.model.enums.StatusType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {

    private String email;
    private String firstName;
    private String lastName;

    private Long bookingId;

    private UserType userType;
    private StatusType statusType;

    private LocalDateTime bookingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String location;
    private String destination;
}
