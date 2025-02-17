package com.example.booking.service.impl;

import com.example.booking.model.Hotel;
import com.example.booking.repository.HotelRepository;
import com.example.booking.repository.HotelSpecification;
import com.example.booking.service.HotelService;
import com.example.booking.utils.BeanUtils;
import com.example.booking.web.dto.filter.HotelFilter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class DatabaseHotelService implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> filterBy(HotelFilter filter, int pageSize, int pageNumber){
        return hotelRepository.findAll(
                HotelSpecification.withFilter(filter),
                PageRequest.of(pageNumber, pageSize))
                .getContent();
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

        findById(id);
        hotelRepository.deleteById(id);
    }

    @Override
    public Hotel rate(Long id, Integer mark) {

        Hotel hotel = findById(id);

        double rating = hotel.getRating();

        int numberOfRatings = hotel.getNumberOfRatings();

        if (rating > 0 && numberOfRatings > 0) {

            double totalRating = rating * numberOfRatings;
            totalRating = totalRating - rating + mark;
            rating = Math.round(totalRating / numberOfRatings * 10) / 10.0;
            numberOfRatings = numberOfRatings + 1;

        } else {
            rating =  mark;
            numberOfRatings = 1;
        }

        hotel.setRating(rating);
        hotel.setNumberOfRatings(numberOfRatings);

        return update(hotel);
    }
}
