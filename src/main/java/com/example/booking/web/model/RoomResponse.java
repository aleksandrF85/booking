package com.example.booking.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

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