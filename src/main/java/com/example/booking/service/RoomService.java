package com.example.booking.service;

import com.example.booking.model.Hotel;
import com.example.booking.model.Room;
import com.example.booking.web.dto.filter.RoomFilter;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    List<Room> findAll();

    Room findById(Long id);

    List<Room> filterBy(RoomFilter filter, int pageSize, int pageNumber);

    Room findByHotelAndNumber(Hotel hotel, int number);

    Room save(Room room, String hotelName);

    Room update(Room room);

    void deleteById(Long id);

    void checkCapacity(Room room, int guestAmount);

    void checkDates(LocalDate checkIn, LocalDate checkOut);

    void setUnavailableDates(Room room, List<LocalDate> bookingDates);

    void removeBookingDates(Room room, List<LocalDate> bookingDates);
}
