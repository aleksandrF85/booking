package com.example.booking.web.model.filter;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelFilter {

    private Long id;

    private String name;

    private String listingTitle;

    private String city;

    private String address;

    private double distanceFromTheCityCenter;

    private double rating;

    private int numberOfRatings;
}
