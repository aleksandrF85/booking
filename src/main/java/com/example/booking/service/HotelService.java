package com.example.booking.service;


import com.example.booking.model.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> findAll();

    Hotel findById(Long id);
    Hotel findByName(String name);

    Hotel save(Hotel hotel);

    Hotel update(Hotel hotel);

    void deleteById(Long id);
}
