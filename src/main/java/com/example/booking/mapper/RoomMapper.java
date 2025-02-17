package com.example.booking.mapper;

import com.example.booking.model.Room;
import com.example.booking.web.dto.RoomListResponse;
import com.example.booking.web.dto.RoomResponse;
import com.example.booking.web.dto.UpsertRoomRequest;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    Room requestToRoom(UpsertRoomRequest request);

    Room requestToRoom(Long id, UpsertRoomRequest request);

    RoomResponse roomToResponse(Room room);

    default RoomListResponse roomListToRoomListResponse(List<Room> rooms) {
        RoomListResponse response = new RoomListResponse();
        response.setRooms(rooms.stream()
                .map(this::roomToResponse).collect(Collectors.toList()));

        return response;
    }

}