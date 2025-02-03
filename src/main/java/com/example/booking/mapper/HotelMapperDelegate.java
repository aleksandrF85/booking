package com.example.booking.mapper;

import com.example.booking.model.Hotel;
import com.example.booking.web.model.HotelResponse;
import com.example.booking.web.model.UpsertHotelRequest;

public abstract class HotelMapperDelegate implements HotelMapper{

    @Override
    public Hotel requestToHotel(UpsertHotelRequest request) {

        Hotel hotel = new Hotel();

        hotel.setName(request.getName());
        hotel.setListingTitle(request.getListingTitle());
        hotel.setCity(request.getCity());
        hotel.setAddress(request.getAddress());
        hotel.setDistanceFromTheCityCenter(request.getDistanceFromTheCityCenter());

        return hotel;
    }

    @Override
    public Hotel requestToHotel(Long id, UpsertHotelRequest request) {

        Hotel hotel = requestToHotel(request);
        hotel.setId(id);

        return hotel;
    }

    @Override
    public HotelResponse hotelToResponse(Hotel hotel) {

        HotelResponse response = new HotelResponse();

        response.setId(hotel.getId());
        response.setName(hotel.getName());
        response.setListingTitle(hotel.getListingTitle());
        response.setCity(hotel.getCity());
        response.setAddress(hotel.getAddress());
        response.setDistanceFromTheCityCenter(hotel.getDistanceFromTheCityCenter());
        response.setRating(hotel.getRating());
        response.setNumberOfRatings(hotel.getNumberOfRatings());

        return response;
    }

}
