package com.patika.bookingservice.controller;

import com.patika.bookingservice.dto.request.TravelRequest;
import com.patika.bookingservice.dto.response.GenericResponse;
import com.patika.bookingservice.dto.response.TravelResponse;
import com.patika.bookingservice.model.enums.TravelType;
import com.patika.bookingservice.service.TravelService;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(TravelController.class)
public class TravelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TravelService travelService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTravel_Success() throws Exception {
        TravelRequest travelRequest = new TravelRequest();
        TravelResponse travelResponse = new TravelResponse();
        when(travelService.createTravel(any(TravelRequest.class))).thenReturn(travelResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/travels")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(travelRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andDo(print());

        verify(travelService, times(1)).createTravel(any(TravelRequest.class));
    }

    @Test
    void testAddUserToTravel_Success() throws Exception {
        TravelResponse travelResponse = new TravelResponse();
        when(travelService.addUserToTravel(anyLong(), anyString())).thenReturn(travelResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/travels/addUserToTravel")
                        .param("travelId", "1")
                        .param("email", "user@example.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andDo(print());

        verify(travelService, times(1)).addUserToTravel(anyLong(), anyString());
    }

    @Test
    void testGetTravelsByStartCity_Success() throws Exception {
        TravelResponse travelResponse = new TravelResponse();
        List<TravelResponse> travelResponses = Collections.singletonList(travelResponse);
        when(travelService.getTravelsByStartCity(anyString())).thenReturn(travelResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travels/search/cityStartPoint/{city}", "City A"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andDo(print());

        verify(travelService, times(1)).getTravelsByStartCity(anyString());
    }

    @Test
    void testGetTravelsByDestinationCity_Success() throws Exception {
        TravelResponse travelResponse = new TravelResponse();
        List<TravelResponse> travelResponses = Collections.singletonList(travelResponse);
        when(travelService.getTravelsByDestinationCity(anyString())).thenReturn(travelResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travels/search/cityDestinationPoint/{city}", "City B"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andDo(print());

        verify(travelService, times(1)).getTravelsByDestinationCity(anyString());
    }

    @Test
    void testGetTravelsByStartAndDestinationCity_Success() throws Exception {
        TravelResponse travelResponse = new TravelResponse();
        List<TravelResponse> travelResponses = Collections.singletonList(travelResponse);
        when(travelService.getTravelsByStartAndDestinationCity(anyString(), anyString())).thenReturn(travelResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travels/search/cityStartAndDestinationPoint/start/{startCity}/destination/{destinationCity}", "City A", "City B"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andDo(print());

        verify(travelService, times(1)).getTravelsByStartAndDestinationCity(anyString(), anyString());
    }

    @Test
    void testGetTravelsByStartDate_Success() throws Exception {
        LocalDateTime date = LocalDateTime.now();
        TravelResponse travelResponse = new TravelResponse();
        List<TravelResponse> travelResponses = Collections.singletonList(travelResponse);
        when(travelService.getTravelsByStartDate(any(LocalDateTime.class))).thenReturn(travelResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travels/search/startDate/{date}", date.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andDo(print());

        verify(travelService, times(1)).getTravelsByStartDate(any(LocalDateTime.class));
    }

    @Test
    void testGetTravelsByEndDate_Success() throws Exception {
        LocalDateTime date = LocalDateTime.now();
        TravelResponse travelResponse = new TravelResponse();
        List<TravelResponse> travelResponses = Collections.singletonList(travelResponse);
        when(travelService.getTravelsByEndDate(any(LocalDateTime.class))).thenReturn(travelResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travels/search/endDate/{date}", date.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andDo(print());

        verify(travelService, times(1)).getTravelsByEndDate(any(LocalDateTime.class));
    }

    @Test
    void testGetTravelsByStartAndEndDate_Success() throws Exception {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        TravelResponse travelResponse = new TravelResponse();
        List<TravelResponse> travelResponses = Collections.singletonList(travelResponse);
        when(travelService.getTravelsByStartAndEndDate(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(travelResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travels/search/startAndEndDate/startDate/{startDate}/endDate/{endDate}", startDate.toString(), endDate.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andDo(print());

        verify(travelService, times(1)).getTravelsByStartAndEndDate(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testGetTravelsByType_Success() throws Exception {
        TravelType travelType = TravelType.BUS;
        TravelResponse travelResponse = new TravelResponse();
        List<TravelResponse> travelResponses = Collections.singletonList(travelResponse);
        when(travelService.getTravelsByType(any(TravelType.class))).thenReturn(travelResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travels/search/travelTypes/{travelType}", travelType.name()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andDo(print());

        verify(travelService, times(1)).getTravelsByType(any(TravelType.class));
    }
}
