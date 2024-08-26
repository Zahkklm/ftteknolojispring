package com.patika.bookingservice.service;

import com.patika.bookingservice.client.user.UserClient;
import com.patika.bookingservice.converter.TravelConverter;
import com.patika.bookingservice.dto.request.TravelRequest;
import com.patika.bookingservice.dto.response.GenericResponse;
import com.patika.bookingservice.dto.response.TravelResponse;
import com.patika.bookingservice.dto.response.UserResponse;
import com.patika.bookingservice.model.Booking;
import com.patika.bookingservice.model.Travel;
import com.patika.bookingservice.model.enums.TravelType;
import com.patika.bookingservice.repository.BookingRepository;
import com.patika.bookingservice.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelService {

    private final BookingRepository bookingRepository;
    private final TravelRepository travelRepository;
    private final UserClient userClient;

    public TravelResponse addUserToTravel(Long travelId, String userEmail) {

        // ask user-service to return user entity object
        GenericResponse<UserResponse> userResponse = userClient.getUserByEmail(userEmail);

        // userResponse.getData() to access inside userResponse
        Optional<Travel> travel = travelRepository.findById(travelId);
        Travel foundTravel = travel.get();

        List<UserResponse> newUserList = new ArrayList<>();
        newUserList = foundTravel.getUserDTO();
        newUserList.add(userResponse.getData()); // add found user to the list of passengers/users

        foundTravel.setUserDTO(newUserList);
        travelRepository.save(foundTravel);

        return TravelConverter.toResponse(foundTravel);
    }

    public TravelResponse createTravel(TravelRequest travelRequest) {
        // create empty travel object

        List<UserResponse> emptyList = new ArrayList<>();
        Set<Booking> emptySet = new HashSet<>();

        Travel travel = Travel.builder()
                .travelType(travelRequest.getTravelType())
                .startTime(travelRequest.getStartTime())
                .endTime(travelRequest.getEndTime())
                .location(travelRequest.getLocation())
                .destination(travelRequest.getDestination())
                .userDTO(emptyList) // empty List<> of userDTO
                .bookings(emptySet) // empty Set<> of Booking
                .build();

        travelRepository.save(travel);

        return TravelConverter.toResponse(travel);
    }

    public List<TravelResponse> getTravelsByStartCity(String city) {
        List<Travel> travelList = travelRepository.retrieveAllByLocation(city);

        return TravelConverter.toResponse(travelList);
    }

    public List<TravelResponse> getTravelsByDestinationCity(String city) {
        List<Travel> travelList = travelRepository.retrieveAllByDestination(city);

        return TravelConverter.toResponse(travelList);
    }

    public List<TravelResponse> getTravelsByStartAndDestinationCity(String startCity, String destinationCity) {
        List<Travel> travelList = travelRepository.retrieveAllByLocationAndDestination(startCity, destinationCity);

        return TravelConverter.toResponse(travelList);
    }


    public List<TravelResponse> getTravelsByStartDate(LocalDateTime date) {
        List<Travel> travelList = travelRepository.retrieveAllByStartDate(date);

        return TravelConverter.toResponse(travelList);
    }

    public List<TravelResponse> getTravelsByEndDate(LocalDateTime date) {
        List<Travel> travelList = travelRepository.retrieveAllByEndDate(date);

        return TravelConverter.toResponse(travelList);
    }

    public List<TravelResponse> getTravelsByStartAndEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Travel> travelList = travelRepository.retrieveAllByEndDate(startDate, endDate);

        return TravelConverter.toResponse(travelList);
    }

    public List<TravelResponse> getTravelsByType(TravelType travelType) {
        List<Travel> travelList = travelRepository.retrieveAllByType(travelType.name());

        return TravelConverter.toResponse(travelList);
    }
}
