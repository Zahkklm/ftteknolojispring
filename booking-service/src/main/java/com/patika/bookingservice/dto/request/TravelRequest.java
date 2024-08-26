package com.patika.bookingservice.dto.request;

import com.patika.bookingservice.dto.response.UserResponse;
import com.patika.bookingservice.model.enums.TravelType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelRequest {
    private List<UserResponse> userDTO;
    private TravelType travelType;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String location;
    private String destination;
}
