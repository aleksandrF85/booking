package com.example.booking.service.impl;

import com.example.booking.model.Hotel;
import com.example.booking.repository.HotelRepository;
import com.example.booking.service.HotelService;
import com.example.booking.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseHotelService implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Отель с таким ID {0} не найден!", id
                )));
    }

    @Override
    public Hotel findByName(String name) {
        return hotelRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Отель с таким названием {0} не найден!", name
                )));
    }

    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Hotel hotel) {

        Hotel hotelForUpdate = findById(hotel.getId());

        BeanUtils.copyNonNullProperties(hotel, hotelForUpdate);

        return hotelRepository.save(hotelForUpdate);
    }

    @Override
    public void deleteById(Long id) {

//        findById(id);
        hotelRepository.deleteById(id);
    }
}
