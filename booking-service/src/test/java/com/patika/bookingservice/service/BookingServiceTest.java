package com.patika.bookingservice.service;

import com.patika.bookingservice.client.notification.NotificationClient;
import com.patika.bookingservice.client.user.UserClient;
import com.patika.bookingservice.dto.request.BookingRequest;
import com.patika.bookingservice.dto.request.enums.NotificationType;
import com.patika.bookingservice.dto.response.BookingResponse;
import com.patika.bookingservice.dto.response.GenericResponse;
import com.patika.bookingservice.dto.response.UserResponse;
import com.patika.bookingservice.exception.BookingException;
import com.patika.bookingservice.exception.ExceptionMessages;
import com.patika.bookingservice.exception.UserException;
import com.patika.bookingservice.model.Booking;
import com.patika.bookingservice.model.Travel;
import com.patika.bookingservice.model.enums.StatusType;
import com.patika.bookingservice.model.enums.TravelType;
import com.patika.bookingservice.model.enums.UserType;
import com.patika.bookingservice.repository.BookingRepository;
import com.patika.bookingservice.repository.TravelRepository;
import com.patika.bookingservice.converter.BookingConverter;
import com.patika.bookingservice.dto.request.NotificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TravelRepository travelRepository;

    @Mock
    private TravelService travelService;

    @Mock
    private UserClient userClient;

    @Mock
    private NotificationClient notificationClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking_Success_Bus() {
        // Given
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setEmail("user@example.com");
        bookingRequest.setTravelId(1L);
        bookingRequest.setFirstName("John");
        bookingRequest.setLastName("Doe");
        bookingRequest.setUserType(UserType.valueOf("VIP"));
        bookingRequest.setStatusType(StatusType.ACTIVE);
        bookingRequest.setBookingDate(LocalDateTime.now());
        bookingRequest.setStartTime(LocalDateTime.now().plusHours(1));
        bookingRequest.setEndTime(LocalDateTime.now().plusHours(2));
        bookingRequest.setLocation("Location A");
        bookingRequest.setDestination("Destination B");

        Travel travel = new Travel();
        travel.setTravelType(TravelType.BUS);
        travel.setUserDTO(new ArrayList<>()); // Empty list

        Booking booking = new Booking();
        booking.setId(1L);
        when(travelRepository.findById(anyLong())).thenReturn(Optional.of(travel));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        doNothing().when(travelService).addUserToTravel(anyLong(), anyString());

        // When
        BookingResponse response = bookingService.createBooking(bookingRequest);

        // Then
        assertNotNull(response);
        verify(notificationClient, times(2)).pushNotification(any(NotificationRequest.class));
    }

    @Test
    void testCreateBooking_Success_Plane() {
        // Given
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setEmail("user@example.com");
        bookingRequest.setTravelId(1L);
        bookingRequest.setFirstName("Jane");
        bookingRequest.setLastName("Doe");
        bookingRequest.setUserType(UserType.valueOf("ADMIN"));
        bookingRequest.setStatusType(StatusType.ACTIVE);
        bookingRequest.setBookingDate(LocalDateTime.now());
        bookingRequest.setStartTime(LocalDateTime.now().plusHours(1));
        bookingRequest.setEndTime(LocalDateTime.now().plusHours(2));
        bookingRequest.setLocation("Location C");
        bookingRequest.setDestination("Destination D");

        Travel travel = new Travel();
        travel.setTravelType(TravelType.PLANE);
        travel.setUserDTO(new ArrayList<>()); // Empty list

        Booking booking = new Booking();
        booking.setId(2L);
        when(travelRepository.findById(anyLong())).thenReturn(Optional.of(travel));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        doNothing().when(travelService).addUserToTravel(anyLong(), anyString());

        // When
        BookingResponse response = bookingService.createBooking(bookingRequest);

        // Then
        assertNotNull(response);
        verify(notificationClient, times(2)).pushNotification(any(NotificationRequest.class));
    }

    @Test
    void testCreateBooking_UserNotFound() {
        // Given
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setEmail("user@example.com");
        bookingRequest.setTravelId(1L);

        when(userClient.getUserByEmail(anyString())).thenThrow(new RuntimeException("User not found"));

        // When & Then
        Exception exception = assertThrows(UserException.class, () -> bookingService.createBooking(bookingRequest));
        assertEquals(ExceptionMessages.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testCreateBooking_TravelNotFound() {
        // Given
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setEmail("user@example.com");
        bookingRequest.setTravelId(1L);

        // When & Then
        Exception exception = assertThrows(BookingException.class, () -> bookingService.createBooking(bookingRequest));
        assertEquals("Sefer bulunamadı.", exception.getMessage());
    }

    @Test
    void testCreateBooking_TravelFull_Bus() {
        // Given
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setEmail("user@example.com");
        bookingRequest.setTravelId(1L);

        Travel travel = new Travel();
        travel.setTravelType(TravelType.BUS);

        // When & Then
        Exception exception = assertThrows(BookingException.class, () -> bookingService.createBooking(bookingRequest));
        assertEquals("Otobüs tamamen dolu!", exception.getMessage());
    }

    @Test
    void testCreateBooking_TravelFull_Plane() {
        // Given
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setEmail("user@example.com");
        bookingRequest.setTravelId(1L);

        Travel travel = new Travel();
        travel.setTravelType(TravelType.PLANE);

        // When & Then
        Exception exception = assertThrows(BookingException.class, () -> bookingService.createBooking(bookingRequest));
        assertEquals("Uçak tamamen dolu!", exception.getMessage());
    }

    @Test
    void testCancelBooking_Success() {
        // Given
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setStatusType(StatusType.ACTIVE);

        when(bookingRepository.findByBookingId(anyLong())).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // When
        BookingResponse response = bookingService.cancelBooking(bookingId);

        // Then
        assertNotNull(response);
        assertEquals(StatusType.IN_ACTIVE, response.getStatusType());
    }

    @Test
    void testCancelBooking_BookingNotFound() {
        // Given
        Long bookingId = 1L;
        when(bookingRepository.findByBookingId(anyLong())).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> bookingService.cancelBooking(bookingId));
        assertEquals("Böyle bir sefer bulunamadığı için iptal işlemi iptal edildi.", exception.getMessage());
    }

    @Test
    void testGetByBookingId_Success() {
        // Given
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setId(bookingId);

        when(bookingRepository.findByBookingId(anyLong())).thenReturn(Optional.of(booking));

        // When
        BookingResponse response = bookingService.getByBookingId(bookingId);

        // Then
        assertNotNull(response);
    }

    @Test
    void testGetByBookingId_NotFound() {
        // Given
        Long bookingId = 1L;
        when(bookingRepository.findByBookingId(anyLong())).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> bookingService.getByBookingId(bookingId));
        assertEquals("Böyle bir sefer bulunamadı.", exception.getMessage());
    }

    @Test
    void testGetBookingsByEmail_Success() {
        // Given
        String email = "user@example.com";
        Booking booking = new Booking();
        booking.setEmail(email);

        when(bookingRepository.findBookingsByEmail(anyString())).thenReturn(Optional.of(Collections.singletonList(booking)));

        // When
        List<BookingResponse> responses = bookingService.getBookingsByEmail(email);

        // Then
        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    void testGetBookingsByEmail_NotFound() {
        // Given
        String email = "user@example.com";
        when(bookingRepository.findBookingsByEmail(anyString())).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> bookingService.getBookingsByEmail(email));
        assertEquals("Bu email adresine ait sefer bilgisi bulunamadı.", exception.getMessage());
    }

    @Test
    void testGetAllBookings_Success() {
        // Given
        Booking booking = new Booking();
        when(bookingRepository.findAll()).thenReturn(Collections.singletonList(booking));

        // When
        List<BookingResponse> responses = bookingService.getAllBookings();

        // Then
        assertNotNull(responses);
        assertEquals(1, responses.size());
    }
}
