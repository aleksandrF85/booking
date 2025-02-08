package com.example.booking.mapper;

import com.example.booking.model.Room;
import com.example.booking.web.model.RoomResponse;
import com.example.booking.web.model.UpsertRoomRequest;

public abstract class RoomMapperDelegate implements RoomMapper{


    @Override
    public Room requestToRoom(UpsertRoomRequest request) {

        Room room = new Room();
        room.setName(request.getName());
        room.setDescription(request.getDescription());
        room.setNumber(request.getNumber());
        room.setPrice(request.getPrice());
        room.setMaxCapacity(request.getMaxCapacity());

        return room;
    }

    @Override
    public Room requestToRoom(Long id, UpsertRoomRequest request) {

        Room room = requestToRoom(request);
        room.setId(id);

        return room;
    }

    @Override
    public RoomResponse roomToResponse(Room room) {

        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setName(room.getName());
        response.setDescription(room.getDescription());
        response.setNumber(room.getNumber());
        response.setPrice(room.getPrice());
        response.setMaxCapacity(room.getMaxCapacity());
        response.setHotelName(room.getHotel().getName());

        return response;
    }
}
