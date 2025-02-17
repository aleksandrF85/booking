package com.example.booking.web.controller;

import com.example.booking.mapper.BookingMapper;
import com.example.booking.model.Booking;
import com.example.booking.service.BookingService;
import com.example.booking.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService databaseBookingService;

    private final BookingMapper bookingMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<BookingListResponse> findAll() {
        return ResponseEntity.ok(
                bookingMapper.bookingListToBookingListResponse(
                        databaseBookingService.findAll()
                )
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody @Valid UpsertBookingRequest request,
                                                @AuthenticationPrincipal UserDetails userDetails) {

        Booking newBooking = bookingMapper.requestToBooking(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingMapper.bookingToResponse(databaseBookingService.save(newBooking, userDetails.getUsername())));
    }

}
