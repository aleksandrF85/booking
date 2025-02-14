package com.example.booking.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingEvent {

    private Long userId;

    private Long roomId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private LocalDateTime eventDateTime;
}
