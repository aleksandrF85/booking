package com.example.booking.mapper;

import com.example.booking.model.Booking;
import com.example.booking.web.dto.*;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(BookingMapperDelegate.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    Booking requestToBooking(UpsertBookingRequest request);

    Booking requestToBooking(Long id, UpsertBookingRequest request);

    BookingResponse bookingToResponse(Booking booking);

    default BookingListResponse bookingListToBookingListResponse(List<Booking> bookings) {
        BookingListResponse response = new BookingListResponse();
        response.setBookingList(bookings.stream()
                .map(this::bookingToResponse).collect(Collectors.toList()));

        return response;
    }
}
