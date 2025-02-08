package com.example.booking.repository;

import com.example.booking.model.Hotel;
import com.example.booking.web.model.filter.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter hotelFilter){
        return Specification.where(byHotelId(hotelFilter.getId()))
                .and(byHotelName(hotelFilter.getName()))
                .and(byListingTitle(hotelFilter.getListingTitle()))
                .and(byCity(hotelFilter.getCity()))
                .and(byAddress(hotelFilter.getAddress()))
                .and(byDistanceFromTheCityCenter(hotelFilter.getDistanceFromTheCityCenter()))
                .and(byRating(hotelFilter.getRating()))
                .and(byNumberOfRatings(hotelFilter.getNumberOfRatings()));
    }

    static Specification<Hotel> byHotelId(Long id) {
        return ((root, query, criteriaBuilder) -> {
            if (id == null || id <= 0) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), id);
        });
    }

    static Specification<Hotel> byHotelName(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("name"), name);
        });
    }

    static Specification<Hotel> byListingTitle(String listingTitle) {
        return ((root, query, criteriaBuilder) -> {
            if (listingTitle == null || listingTitle.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("listingTitle"), listingTitle);
        });
    }

    static Specification<Hotel> byCity(String city) {
        return ((root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("city"), city);
        });
    }

    static Specification<Hotel> byAddress(String address) {
        return ((root, query, criteriaBuilder) -> {
            if (address == null || address.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("address"), address);
        });
    }

    static Specification<Hotel> byDistanceFromTheCityCenter(Double distanceFromTheCityCenter) {
        return ((root, query, criteriaBuilder) -> {
            if (distanceFromTheCityCenter == null || distanceFromTheCityCenter <= 0) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("distanceFromTheCityCenter"), distanceFromTheCityCenter);
        });
    }

    static Specification<Hotel> byRating(Double rating) {
        return ((root, query, criteriaBuilder) -> {
            if (rating == null || rating <= 0) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating);
        });
    }

    static Specification<Hotel> byNumberOfRatings(Integer numberOfRatings) {
        return ((root, query, criteriaBuilder) -> {
            if (numberOfRatings == null || numberOfRatings <= 0) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfRatings"), numberOfRatings);
        });
    }

}
