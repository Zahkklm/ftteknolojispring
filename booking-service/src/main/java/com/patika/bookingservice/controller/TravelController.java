package com.patika.bookingservice.controller;

import com.patika.bookingservice.dto.request.BookingRequest;
import com.patika.bookingservice.dto.request.TravelRequest;
import com.patika.bookingservice.dto.response.BookingResponse;
import com.patika.bookingservice.dto.response.GenericResponse;
import com.patika.bookingservice.dto.response.TravelResponse;
import com.patika.bookingservice.model.enums.TravelType;
import com.patika.bookingservice.service.BookingService;
import com.patika.bookingservice.service.TravelService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/travels")
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @PostMapping // FOR ADMIN
    public GenericResponse<TravelResponse> createTravel(@RequestBody TravelRequest travelRequest) {
        return GenericResponse.success(travelService.createTravel(travelRequest), HttpStatus.OK);
    }

    @PutMapping("/addUserToTravel")
    GenericResponse<TravelResponse> addUserTravel(@PathParam("travelId") Long travelId, @PathParam("email") String email) {
        return GenericResponse.success(travelService.addUserToTravel(travelId, email), HttpStatus.OK);
    }

    @GetMapping("/search/cityStartPoint/{city}")
    GenericResponse<List<TravelResponse>> getTravelsByStartCity(@PathVariable String city) {
        return GenericResponse.success(travelService.getTravelsByStartCity(city), HttpStatus.OK);
    }

    @GetMapping("/search/cityDestinationPoint/{city}")
    GenericResponse<List<TravelResponse>> getTravelsByDestinationCity(@PathVariable String city) {
        return GenericResponse.success(travelService.getTravelsByDestinationCity(city), HttpStatus.OK);
    }

    @GetMapping("/search/cityStartAndDestinationPoint/start/{startCity}/destination/{destinationCity}")
    GenericResponse<List<TravelResponse>> getTravelsByStartAndDestinationCity(@PathVariable String startCity, @PathVariable String destinationCity) {
        return GenericResponse.success(travelService.getTravelsByStartAndDestinationCity(startCity, destinationCity), HttpStatus.OK);
    }

    @GetMapping("/search/startDate/{date}")
    GenericResponse<List<TravelResponse>> getTravelsByStartDate(@PathVariable LocalDateTime date) {
        return GenericResponse.success(travelService.getTravelsByStartDate(date), HttpStatus.OK);
    }

    @GetMapping("/search/endDate/{date}")
    GenericResponse<List<TravelResponse>> getTravelsByEndDate(@PathVariable LocalDateTime date) {
        return GenericResponse.success(travelService.getTravelsByEndDate(date), HttpStatus.OK);
    }

    @GetMapping("/search/startAndEndDate/startDate/{startDate}/endDate/{endDate}")
    GenericResponse<List<TravelResponse>> getTravelsByStartAndEndDate(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        return GenericResponse.success(travelService.getTravelsByStartAndEndDate(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/search/travelTypes/{travelType}")
    GenericResponse<List<TravelResponse>> getTravelsByType(@PathVariable TravelType travelType) {
        return GenericResponse.success(travelService.getTravelsByType(travelType), HttpStatus.OK);
    }


}
