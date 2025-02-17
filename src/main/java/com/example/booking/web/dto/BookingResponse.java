package com.example.booking.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {

    private String hotelName;

    private int roomNumber;

    private String username;

    private LocalDate checkIn;

    private LocalDate checkOut;



}
