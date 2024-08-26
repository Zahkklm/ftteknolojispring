package com.patika.bookingservice.model;

import com.patika.bookingservice.model.enums.UserType;
import com.patika.bookingservice.model.enums.StatusType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "travel_id")  // Column name in the bookings table
    private Travel travel;

    private String email;

    private String firstName;
    private String lastName;

    private String location;
    private String destination;

    private LocalDateTime bookingDate; // current date - 2024-08-18T10:00:00
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType statusType;


}
