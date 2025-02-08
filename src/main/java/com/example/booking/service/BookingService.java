package com.example.booking.service;

import com.example.booking.model.Booking;
import com.example.booking.model.Room;
import com.example.booking.model.User;

import java.util.List;


public interface BookingService {

    List<Booking> findAll();

    List<Booking> findByUser(User user);

    Booking findById(Long id);

    Booking save(Booking booking, String username);

    Booking update(Booking booking);

    void deleteById(Long id);
}
