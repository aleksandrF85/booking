package com.example.booking.web.dto;

import lombok.Data;

@Data
public class RoomResponse{

    private Long id;

    private String name;

    private String description;

    private int number;

    private int price;

    private int maxCapacity;

//    private List<List<Date>> unavailableDates;

    private String hotelName;
}