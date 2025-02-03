package com.example.booking.service;

import com.example.booking.model.Hotel;
import com.example.booking.model.Room;

import java.util.List;

public interface RoomService {

    List<Room> findAll();

    Room findById(Long id);

    Room save(Room room, String hotelName);

    Room update(Room room);

    void deleteById(Long id);
}
