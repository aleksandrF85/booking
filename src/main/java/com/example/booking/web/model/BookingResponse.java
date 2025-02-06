package com.example.booking.web.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BookingResponse {

    private String hotelName;

    private int roomNumber;

    private String username;

    private LocalDate checkIn;

    private LocalDate checkOut;



}
