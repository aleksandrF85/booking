package com.example.booking.repository;

import com.example.booking.model.Hotel;
import com.example.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    Optional<Room> findByHotelAndNumber(Hotel hotel, int number);
}