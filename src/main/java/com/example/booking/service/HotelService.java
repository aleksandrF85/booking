package com.example.booking.service;


import com.example.booking.model.Hotel;
import com.example.booking.web.model.filter.HotelFilter;

import java.util.List;

public interface HotelService {

    List<Hotel> findAll();

    List<Hotel> filterBy(HotelFilter filter, int pageSize, int pageNumber);

    Hotel findById(Long id);

    Hotel findByName(String name);

    Hotel save(Hotel hotel);

    Hotel update(Hotel hotel);

    void deleteById(Long id);

    Hotel rate(Long id, Integer mark);
}
