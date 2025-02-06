package com.example.booking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity;

    @Column(name = "unavailable_dates", nullable = false)
    private List<LocalDate> unavailableDates = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "room")
    @ToString.Exclude
    private List<Booking> bookingList = new ArrayList<>();
}