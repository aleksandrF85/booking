package com.example.booking.web.controller;

import com.example.booking.mapper.RoomMapper;
import com.example.booking.model.Room;
import com.example.booking.service.RoomService;
import com.example.booking.web.dto.*;
import com.example.booking.web.dto.filter.PageFilter;
import com.example.booking.web.dto.filter.RoomFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService databaseRoomService;

    private final RoomMapper roomMapper;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<RoomListResponse> findAll(@RequestBody RoomFilter roomFilter,
                                                    @Valid PageFilter pageFilter) {
        return ResponseEntity.ok(roomMapper.roomListToRoomListResponse(
                        databaseRoomService.filterBy(
                                roomFilter, pageFilter.getPageSize(), pageFilter.getPageNumber())
                )
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById (@PathVariable Long id) {
        return ResponseEntity.ok(roomMapper.roomToResponse(
                databaseRoomService.findById(id)
        ));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody @Valid UpsertRoomRequest request,
                                                @RequestParam String hotelName) {

        Room newRoom = roomMapper.requestToRoom(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.roomToResponse(databaseRoomService.save(newRoom, hotelName)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id,
                                                @RequestBody UpsertRoomRequest request) {

        Room updatedRoom = databaseRoomService.update(roomMapper.requestToRoom(id, request));

        return ResponseEntity.ok(roomMapper.roomToResponse(updatedRoom));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        databaseRoomService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}

