package com.patika.bookingservice.model;

import com.patika.bookingservice.dto.response.UserResponse;
import com.patika.bookingservice.model.enums.StatusType;
import com.patika.bookingservice.model.enums.TravelType;
import com.patika.bookingservice.model.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.search.PostgreSQLTSVectorType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "travels")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelId;

    @OneToMany(mappedBy = "travel")
    private Set<Booking> bookings;

    @Enumerated(EnumType.STRING)
    private TravelType travelType;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String location;
    private String destination;

    @ElementCollection
    @CollectionTable(name = "travel_users", joinColumns = @JoinColumn(name = "travel_id"))
    private List<UserResponse> userDTO;

    @Type(PostgreSQLTSVectorType.class)
    @Column(name = "title_search_vector",columnDefinition = "tsvector")
    private String titleSearchVector;
}
