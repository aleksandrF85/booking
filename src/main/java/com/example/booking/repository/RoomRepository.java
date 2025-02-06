package com.example.booking.repository;

import com.example.booking.model.Hotel;
import com.example.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByHotelAndNumber(Hotel hotel, int number);
}