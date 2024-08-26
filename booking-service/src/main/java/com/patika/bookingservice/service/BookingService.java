package com.patika.bookingservice.service;

import com.patika.bookingservice.client.notification.NotificationClient;
import com.patika.bookingservice.dto.request.NotificationRequest;
import com.patika.bookingservice.dto.request.enums.NotificationType;
import com.patika.bookingservice.client.user.UserClient;
import com.patika.bookingservice.converter.BookingConverter;
import com.patika.bookingservice.dto.request.BookingRequest;
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
import com.patika.bookingservice.repository.BookingRepository;
import com.patika.bookingservice.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TravelRepository travelRepository;
    private final TravelService travelService;
    private final UserClient userClient;
    private final NotificationClient notificationClient;

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        // ask user-service if user exists
        GenericResponse<UserResponse> user;
        try{
            user = userClient.getUserByEmail(bookingRequest.getEmail());
        }
        catch (Exception e){
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        }

        Optional<Travel> travel = travelRepository.findById(bookingRequest.getTravelId());
        Travel foundTravel = travel.get();

        // System Passenger Limit - 45 BUS - 189 PLANE
        if(foundTravel.getTravelType() == TravelType.BUS){
            if(foundTravel.getUserDTO().size() >= 45){
                throw new BookingException("Otobüs tamamen dolu!");
            }
        }
        else if(foundTravel.getTravelType() == TravelType.PLANE){
            if(foundTravel.getUserDTO().size() >= 189){
                throw new BookingException("Uçak tamamen dolu!");
            }
        }


        Booking booking = Booking.builder()
                .email(bookingRequest.getEmail())
                .firstName(bookingRequest.getFirstName())
                .lastName(bookingRequest.getLastName())
                .userType(bookingRequest.getUserType())
                .statusType(bookingRequest.getStatusType())
                .bookingDate(bookingRequest.getBookingDate())
                .startTime(bookingRequest.getStartTime())
                .endTime(bookingRequest.getEndTime())
                .location(bookingRequest.getLocation())
                .destination(bookingRequest.getDestination())
                .travel(foundTravel)
                .build();

        travelService.addUserToTravel(bookingRequest.getTravelId(), bookingRequest.getEmail()); // add user to list of users in that travel

        bookingRepository.save(booking);

        //send rabbitMQ message
        //rabbitMqProducer.sendEmail(new SendEmailMessage(bookingRequest.getEmail(), EmailTemplate.CREATE_USER_TEMPLATE));

        //NotificationRequest notificationRequest = new NotificationRequest("user@example.com", "Biletiniz onaylandı.", NotificationType.EMAIL);
        //rabbitTemplate.convertAndSend(rabbitMQConfig.getExchange(), "notification.routingkey", notificationRequest);
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setType(NotificationType.email);
        notificationRequest.setRecipient("ozgur@ozgur.com");
        notificationRequest.setMessage("Sefer ayarlandı");

        notificationClient.pushNotification(notificationRequest); // RabbitMQ email gönder

        notificationRequest.setType(NotificationType.sms);
        notificationClient.pushNotification(notificationRequest); // RabbitMQ SMS gönder

        return BookingConverter.toResponse(booking);
    }

    public BookingResponse cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId).orElseThrow(() -> new RuntimeException(("Böyle bir sefer bulunamadığı için iptal işlemi iptal edildi.")));

        // changeStatus -> IN_ACTIVE
        booking.setStatusType(StatusType.IN_ACTIVE);
        bookingRepository.save(booking);

        return BookingConverter.toResponse(booking);
    }

    public BookingResponse getByBookingId(Long bookingId) {

        Booking booking = bookingRepository.findByBookingId(bookingId).orElseThrow(() -> new RuntimeException("Böyle bir sefer bulunamadı."));

        return BookingConverter.toResponse(booking);
    }


    public List<BookingResponse> getBookingsByEmail(String email) {
        List<BookingResponse> responses = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findBookingsByEmail(email).orElseThrow(() -> new RuntimeException("Bu email adresine ait sefer bilgisi bulunamadı."));

        for(Booking booking : bookings){
            responses.add(BookingConverter.toResponse(booking));
        }

        return responses;
    }

    public List<BookingResponse> getAllBookings()
    {

        List<BookingResponse> responses = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findAll();

        for(Booking booking : bookings){
            responses.add(BookingConverter.toResponse(booking));
        }

        return responses;
    }
}
