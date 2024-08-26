package com.patika.bookingservice.controller;

import com.patika.bookingservice.dto.request.BookingRequest;
import com.patika.bookingservice.dto.response.BookingResponse;
import com.patika.bookingservice.dto.response.GenericResponse;
import com.patika.bookingservice.exception.ExceptionMessages;
import com.patika.bookingservice.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking_Success() throws Exception {
        BookingRequest bookingRequest = new BookingRequest();
        BookingResponse bookingResponse = new BookingResponse();
        when(bookingService.createBooking(any(BookingRequest.class))).thenReturn(bookingResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bookings")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andDo(print());

        verify(bookingService, times(1)).createBooking(any(BookingRequest.class));
    }

    @Test
    void testCancelBooking_Success() throws Exception {
        Long bookingId = 1L;
        BookingResponse bookingResponse = new BookingResponse();
        when(bookingService.cancelBooking(anyLong())).thenReturn(bookingResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bookings/cancel")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andDo(print());

        verify(bookingService, times(1)).cancelBooking(anyLong());
    }

    @Test
    void testGetAllBookings_Success() throws Exception {
        BookingResponse bookingResponse = new BookingResponse();
        List<BookingResponse> bookingResponses = Collections.singletonList(bookingResponse);
        when(bookingService.getAllBookings()).thenReturn(bookingResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bookings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andDo(print());

        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    void testGetBookingsByEmail_Success() throws Exception {
        String email = "user@example.com";
        BookingResponse bookingResponse = new BookingResponse();
        List<BookingResponse> bookingResponses = Collections.singletonList(bookingResponse);
        when(bookingService.getBookingsByEmail(anyString())).thenReturn(bookingResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bookings/{email}", email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andDo(print());

        verify(bookingService, times(1)).getBookingsByEmail(anyString());
    }

    @Test
    void testGetBookingById_Success() throws Exception {
        Long bookingId = 1L;
        BookingResponse bookingResponse = new BookingResponse();
        when(bookingService.getByBookingId(anyLong())).thenReturn(bookingResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bookings/{bookingId}", bookingId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andDo(print());

        verify(bookingService, times(1)).getByBookingId(anyLong());
    }
}
