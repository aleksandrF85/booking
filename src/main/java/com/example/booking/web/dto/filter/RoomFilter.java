package com.example.booking.web.dto.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RoomFilter {

    private Long id;

    private String name;

    private int minPrice;

    private int maxPrice;

    private int maxCapacity;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long hotelId;
}
