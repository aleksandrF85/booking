package com.example.booking.repository;

import com.example.booking.model.Booking;
import com.example.booking.model.Room;
import com.example.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoom(Room room);

    List<Booking> findByUser(User user);
}