package com.example.booking.mapper;

import com.example.booking.model.Room;
import com.example.booking.web.model.RoomListResponse;
import com.example.booking.web.model.RoomResponse;
import com.example.booking.web.model.UpsertRoomRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING/*, uses = {HotelMapper.class}*/)
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


//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    Room partialUpdate(UpsertRoomRequest upsertRoomRequest, @MappingTarget Room room);

}