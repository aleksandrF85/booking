package com.example.booking.web.controller;

import com.example.booking.mapper.HotelMapper;
import com.example.booking.model.Hotel;
import com.example.booking.service.HotelService;
import com.example.booking.web.dto.HotelListResponse;
import com.example.booking.web.dto.HotelResponse;
import com.example.booking.web.dto.UpsertHotelRequest;
import com.example.booking.web.dto.UpsertRatingRequest;
import com.example.booking.web.dto.filter.HotelFilter;
import com.example.booking.web.dto.filter.PageFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService databaseHotelService;

    private final HotelMapper hotelMapper;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<HotelListResponse> findAll() {
        return ResponseEntity.ok(
                hotelMapper.hotelListToHotelListResponse(
                        databaseHotelService.findAll()
                )
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findById (@PathVariable Long id) {
        return ResponseEntity.ok(hotelMapper.hotelToResponse(
                databaseHotelService.findById(id)
        ));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<HotelListResponse> filterBy (@RequestBody HotelFilter hotelFilter,
                                                       @Valid PageFilter pageFilter) {
        return ResponseEntity.ok(hotelMapper.hotelListToHotelListResponse(
                databaseHotelService.filterBy(
                        hotelFilter, pageFilter.getPageSize(), pageFilter.getPageNumber())
        ));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<HotelResponse> create(@RequestBody @Valid UpsertHotelRequest request) {

        Hotel newHotel = hotelMapper.requestToHotel(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelMapper.hotelToResponse(databaseHotelService.save(newHotel)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id,
                                                @RequestBody UpsertHotelRequest request) {

        Hotel updatedHotel = databaseHotelService.update(hotelMapper.requestToHotel(id, request));

        return ResponseEntity.ok(hotelMapper.hotelToResponse(updatedHotel));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        databaseHotelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/rating/{id}")
    public ResponseEntity<HotelResponse> rate (@PathVariable Long id, @RequestBody @Valid UpsertRatingRequest request) {

        return ResponseEntity.ok(hotelMapper.hotelToResponse(
                databaseHotelService.rate(id, request.getMark())
        ));
    }
}
