package com.example.booking.mapper;

import com.example.booking.model.Booking;
import com.example.booking.service.HotelService;
import com.example.booking.service.RoomService;
import com.example.booking.web.dto.BookingResponse;
import com.example.booking.web.dto.UpsertBookingRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BookingMapperDelegate implements BookingMapper{

    @Autowired
    HotelService databaseHotelService;

    @Autowired
    RoomService databaseRoomService;

    @Override
    public Booking requestToBooking(UpsertBookingRequest request) {

        var hotel = databaseHotelService.findByName((request.getHotelName()));
        var room = databaseRoomService.findByHotelAndNumber(hotel, request.getRoomNumber());

        databaseRoomService.checkCapacity(room, request.getGuestAmount());

        databaseRoomService.checkDates(request.getCheckIn(), request.getCheckOut());

        Booking booking = new Booking();

        booking.setRoom(room);
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());

        return booking;
    }

    @Override
    public Booking requestToBooking(Long id, UpsertBookingRequest request) {

        Booking booking = requestToBooking(request);

        booking.setId(id);

        return booking;
    }

    @Override
    public BookingResponse bookingToResponse(Booking booking) {

        BookingResponse response = new BookingResponse();

        response.setUsername(booking.getUser().getUsername());
        response.setHotelName(booking.getRoom().getHotel().getName());
        response.setRoomNumber(booking.getRoom().getNumber());
        response.setCheckIn(booking.getCheckIn());
        response.setCheckOut(booking.getCheckOut());

        return response;
    }
}
