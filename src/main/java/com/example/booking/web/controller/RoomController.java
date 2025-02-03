package com.example.booking.web.controller;

import com.example.booking.mapper.RoomMapper;
import com.example.booking.model.Room;
import com.example.booking.service.RoomService;
import com.example.booking.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService databaseRoomService;

    private final RoomMapper roomMapper;

//    @GetMapping
//    public ResponseEntity<RoomListResponse> findAll() {
//        return ResponseEntity.ok(
//                roomMapper.roomListToRoomListResponse(
//                        databaseRoomService.findAll()
//                )
//        );
//    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById (@PathVariable Long id) {
        return ResponseEntity.ok(roomMapper.roomToResponse(
                databaseRoomService.findById(id)
        ));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody @Valid UpsertRoomRequest request,
                                                @PathVariable String hotelName) {

        Room newRoom = roomMapper.requestToRoom(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.roomToResponse(databaseRoomService.save(newRoom, hotelName)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id,
                                                @RequestBody UpsertRoomRequest request) {

        Room updatedRoom = databaseRoomService.update(roomMapper.requestToRoom(id, request));

        return ResponseEntity.ok(roomMapper.roomToResponse(updatedRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        databaseRoomService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}

