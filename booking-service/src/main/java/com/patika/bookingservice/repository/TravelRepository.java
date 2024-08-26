package com.patika.bookingservice.repository;

import com.patika.bookingservice.model.Booking;
import com.patika.bookingservice.model.Travel;
import com.patika.bookingservice.model.enums.TravelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Long>, JpaSpecificationExecutor<Travel> {
    @Query("SELECT t FROM Travel t WHERE t.location = ?1")
    List<Travel> retrieveAllByLocation(String location);

    @Query("SELECT t FROM Travel t WHERE t.destination = ?1")
    List<Travel> retrieveAllByDestination(String destination);

    @Query("SELECT t FROM Travel t WHERE t.location = ?1 and t.destination = ?2")
    List<Travel> retrieveAllByLocationAndDestination(String location, String destination);

    @Query("SELECT t FROM Travel t WHERE t.startTime = ?1")
    List<Travel> retrieveAllByStartDate(LocalDateTime localDateTime);

    @Query("SELECT t FROM Travel t WHERE t.endTime = ?1")
    List<Travel> retrieveAllByEndDate(LocalDateTime localDateTime);

    @Query("SELECT t FROM Travel t WHERE t.startTime = ?1 and t.endTime = ?2")
    List<Travel> retrieveAllByEndDate(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT t FROM Travel t WHERE t.travelType = ?1")
    List<Travel> retrieveAllByType(String travelType);
}
