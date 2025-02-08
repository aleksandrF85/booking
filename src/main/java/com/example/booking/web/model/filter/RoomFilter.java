package com.example.booking.web.model.filter;

import com.example.booking.model.Hotel;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
