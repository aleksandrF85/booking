package com.example.booking.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "listing_title", nullable = false)
    private String listingTitle;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "distance", nullable = false)
    private double distanceFromTheCityCenter;

    @Column(name = "rating")
    private double rating;

    @Column(name = "numberOfRatings")
    private int numberOfRatings;



}