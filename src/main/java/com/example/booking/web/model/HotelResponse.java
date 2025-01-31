package com.example.booking.web.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class HotelResponse {

    private Long id;

    private String name;

    private String listingTitle;

    private String city;

    private String address;

    private double distanceFromTheCityCenter;

    private double rating;

    private int numberOfRatings;
}
