package com.patika.bookingservice.dto.response;

import com.patika.bookingservice.dto.response.enums.Role;
import com.patika.bookingservice.model.enums.StatusType;
import com.patika.bookingservice.model.enums.UserType;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class UserResponse {

    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private UserType userType;
    private StatusType statusType;
    private Role role;
}
