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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TravelServiceTest {

    @InjectMocks
    private TravelService travelService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TravelRepository travelRepository;

    @Mock
    private UserClient userClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUserToTravel_Success() {
        // Given
        Long travelId = 1L;
        String userEmail = "user@example.com";
        UserResponse userResponse = new UserResponse();
        Travel travel = new Travel();
        travel.setUserDTO(new ArrayList<>());

        UserResponse ur = null;

        when(travelRepository.findById(anyLong())).thenReturn(Optional.of(travel));
        when(travelRepository.save(any(Travel.class))).thenReturn(travel);

        // When
        TravelResponse response = travelService.addUserToTravel(travelId, userEmail);

        // Then
        assertNotNull(response);
        assertEquals(1, travel.getUserDTO().size());
        verify(travelRepository, times(1)).save(any(Travel.class));
    }

    @Test
    void testCreateTravel_Success() {
        // Given
        TravelRequest travelRequest = new TravelRequest();
        travelRequest.setTravelType(TravelType.BUS);
        travelRequest.setStartTime(LocalDateTime.now());
        travelRequest.setEndTime(LocalDateTime.now().plusHours(1));
        travelRequest.setLocation("City A");
        travelRequest.setDestination("City B");

        Travel travel = new Travel();
        when(travelRepository.save(any(Travel.class))).thenReturn(travel);

        // When
        TravelResponse response = travelService.createTravel(travelRequest);

        // Then
        assertNotNull(response);
        verify(travelRepository, times(1)).save(any(Travel.class));
    }

    @Test
    void testGetTravelsByStartCity_Success() {
        // Given
        String city = "City A";
        List<Travel> travelList = Collections.singletonList(new Travel());
        when(travelRepository.retrieveAllByLocation(anyString())).thenReturn(travelList);

        // When
        List<TravelResponse> responses = travelService.getTravelsByStartCity(city);

        // Then
        assertNotNull(responses);
        assertEquals(travelList.size(), responses.size());
        verify(travelRepository, times(1)).retrieveAllByLocation(anyString());
    }

    @Test
    void testGetTravelsByDestinationCity_Success() {
        // Given
        String city = "City B";
        List<Travel> travelList = Collections.singletonList(new Travel());
        when(travelRepository.retrieveAllByDestination(anyString())).thenReturn(travelList);

        // When
        List<TravelResponse> responses = travelService.getTravelsByDestinationCity(city);

        // Then
        assertNotNull(responses);
        assertEquals(travelList.size(), responses.size());
        verify(travelRepository, times(1)).retrieveAllByDestination(anyString());
    }

    @Test
    void testGetTravelsByStartAndDestinationCity_Success() {
        // Given
        String startCity = "City A";
        String destinationCity = "City B";
        List<Travel> travelList = Collections.singletonList(new Travel());
        when(travelRepository.retrieveAllByLocationAndDestination(anyString(), anyString())).thenReturn(travelList);

        // When
        List<TravelResponse> responses = travelService.getTravelsByStartAndDestinationCity(startCity, destinationCity);

        // Then
        assertNotNull(responses);
        assertEquals(travelList.size(), responses.size());
        verify(travelRepository, times(1)).retrieveAllByLocationAndDestination(anyString(), anyString());
    }

    @Test
    void testGetTravelsByStartDate_Success() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        List<Travel> travelList = Collections.singletonList(new Travel());
        when(travelRepository.retrieveAllByStartDate(any(LocalDateTime.class))).thenReturn(travelList);

        // When
        List<TravelResponse> responses = travelService.getTravelsByStartDate(date);

        // Then
        assertNotNull(responses);
        assertEquals(travelList.size(), responses.size());
        verify(travelRepository, times(1)).retrieveAllByStartDate(any(LocalDateTime.class));
    }

    @Test
    void testGetTravelsByEndDate_Success() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        List<Travel> travelList = Collections.singletonList(new Travel());
        when(travelRepository.retrieveAllByEndDate(any(LocalDateTime.class))).thenReturn(travelList);

        // When
        List<TravelResponse> responses = travelService.getTravelsByEndDate(date);

        // Then
        assertNotNull(responses);
        assertEquals(travelList.size(), responses.size());
        verify(travelRepository, times(1)).retrieveAllByEndDate(any(LocalDateTime.class));
    }

    @Test
    void testGetTravelsByStartAndEndDate_Success() {
        // Given
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        List<Travel> travelList = Collections.singletonList(new Travel());
        when(travelRepository.retrieveAllByEndDate(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(travelList);

        // When
        List<TravelResponse> responses = travelService.getTravelsByStartAndEndDate(startDate, endDate);

        // Then
        assertNotNull(responses);
        assertEquals(travelList.size(), responses.size());
        verify(travelRepository, times(1)).retrieveAllByEndDate(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testGetTravelsByType_Success() {
        // Given
        TravelType travelType = TravelType.BUS;
        List<Travel> travelList = Collections.singletonList(new Travel());
        when(travelRepository.retrieveAllByType(anyString())).thenReturn(travelList);

        // When
        List<TravelResponse> responses = travelService.getTravelsByType(travelType);

        // Then
        assertNotNull(responses);
        assertEquals(travelList.size(), responses.size());
        verify(travelRepository, times(1)).retrieveAllByType(anyString());
    }
}
