package com.example.booking.web.controller;

import com.example.booking.mapper.HotelMapper;
import com.example.booking.model.Hotel;
import com.example.booking.service.HotelService;
import com.example.booking.web.model.HotelListResponse;
import com.example.booking.web.model.HotelResponse;
import com.example.booking.web.model.UpsertHotelRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService databaseHotelService;

    private final HotelMapper hotelMapper;

    @GetMapping
    public ResponseEntity<HotelListResponse> findAll() {
        return ResponseEntity.ok(
                hotelMapper.hotelListToHotelListResponse(
                        databaseHotelService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findById (@PathVariable Long id) {
        return ResponseEntity.ok(hotelMapper.hotelToResponse(
                databaseHotelService.findById(id)
        ));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<HotelResponse> findByName (@PathVariable String name) {
        return ResponseEntity.ok(hotelMapper.hotelToResponse(
                databaseHotelService.findByName(name)
        ));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> create(@RequestBody @Valid UpsertHotelRequest request) {

        Hotel newHotel = hotelMapper.requestToHotel(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelMapper.hotelToResponse(databaseHotelService.save(newHotel)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id,
                                                @RequestBody UpsertHotelRequest request) {

        Hotel updatedHotel = databaseHotelService.update(hotelMapper.requestToHotel(id, request));

        return ResponseEntity.ok(hotelMapper.hotelToResponse(updatedHotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        databaseHotelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
