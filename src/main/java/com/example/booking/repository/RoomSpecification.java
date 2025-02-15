package com.example.booking.repository;

import com.example.booking.model.Booking;
import com.example.booking.model.Room;
import com.example.booking.web.model.filter.RoomFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
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

    /**Фильтрация по датам заезда и выезда должна учитывать оба поля.
    Если заполнено только одно из полей, фильтрация не срабатывает.
    При выборе дат заезда и выезда нужно показывать только те номера,
    которые свободны в этом временном диапазоне.**/
    static Specification<Room> byAvailableDates(LocalDate checkIn, LocalDate checkOut) {

        return ((root, query, criteriaBuilder) -> {
            if (checkIn == null || checkOut == null) {
                return null;
            }

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Room> subRoot = subquery.from(Room.class);
            Join<Room, Booking> bookings = subRoot.join("bookings");

            subquery.select(subRoot.get("id"))
                    .where(criteriaBuilder.or(
                            criteriaBuilder.and(
                                    criteriaBuilder.lessThanOrEqualTo(bookings.get("checkIn"), checkIn),
                                    criteriaBuilder.greaterThanOrEqualTo(bookings.get("checkOut"), checkIn)
                            ),
                            criteriaBuilder.and(
                                    criteriaBuilder.lessThanOrEqualTo(bookings.get("checkIn"), checkOut),
                                    criteriaBuilder.greaterThanOrEqualTo(bookings.get("checkOut"), checkOut)
                            ),
                            criteriaBuilder.and(
                                    criteriaBuilder.greaterThanOrEqualTo(bookings.get("checkIn"), checkIn),
                                    criteriaBuilder.lessThanOrEqualTo(bookings.get("checkOut"), checkOut)
                            )
                    ));

            return criteriaBuilder.not(root.get("id").in(subquery));
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
