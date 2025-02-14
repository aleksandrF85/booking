package com.example.booking.repository;

import com.example.booking.model.Room;
import com.example.booking.web.model.filter.RoomFilter;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface RoomSpecification {

    static Specification<Room> withFilter(RoomFilter roomFilter){
        return Specification.where(byRoomId(roomFilter.getId()))
                .and(byRoomName(roomFilter.getName()))
                .and(byMaxCapacity(roomFilter.getMaxCapacity()))
                .and(byPriceRange(roomFilter.getMinPrice(), roomFilter.getMaxPrice()))
                .and(byAvailableDates(roomFilter.getCheckIn(), roomFilter.getCheckOut()))
                .and(byHotelId(roomFilter.getHotelId()));
    }


    static Specification<Room> byRoomId(Long id) {
        return ((root, query, criteriaBuilder) -> {
            if (id == null || id <= 0) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), id);
        });
    }

    static Specification<Room> byRoomName(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("name"), name);
        });
    }

    static Specification<Room> byMaxCapacity(Integer maxCapacity) {
        return ((root, query, criteriaBuilder) -> {
            if (maxCapacity == null || maxCapacity <= 0) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("maxCapacity"), maxCapacity);
        });
    }

    static Specification<Room> byPriceRange(Integer minPrice, Integer maxPrice) {
        return ((root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            if (minPrice == null || minPrice <= 0) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
            if (maxPrice == null || maxPrice <= 0) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
            return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        });
    }
    static Specification<Room> byAvailableDates(LocalDate checkIn, LocalDate checkOut) {

        //TODO - Фильтрация по датам заезда и выезда должна учитывать оба поля.
        // Если заполнено только одно из полей, фильтрация не срабатывает.
        // При выборе дат заезда и выезда нужно показывать только те номера,
        // которые свободны в этом временном диапазоне.

        return ((root, query, criteriaBuilder) -> {
            if (checkIn == null || checkOut == null) {
                return null;
            }

            var bookingDates = checkIn.datesUntil(checkOut);

            return criteriaBuilder.and(

//                    criteriaBuilder.isNotMember(bookingDates, root.get("unavailableDates")),
                    criteriaBuilder.isNotMember(checkIn, root.get("unavailableDates")),
                    criteriaBuilder.isNotMember(checkOut, root.get("unavailableDates"))
            );
        });
    }

    static Specification<Room> byHotelId(Long id) {
        return ((root, query, criteriaBuilder) -> {
            if (id == null || id <= 0) {
                return null;
            }
            return criteriaBuilder.equal(root.get("hotel").get("id"), id);
        });
    }

}
