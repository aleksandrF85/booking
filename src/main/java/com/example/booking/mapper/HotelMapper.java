package com.example.booking.mapper;
import com.example.booking.model.Hotel;
import com.example.booking.web.model.HotelListResponse;
import com.example.booking.web.model.HotelResponse;
import com.example.booking.web.model.UpsertHotelRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(HotelMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel requestToHotel(UpsertHotelRequest request);

    Hotel requestToHotel(Long id, UpsertHotelRequest request);

    HotelResponse hotelToResponse(Hotel hotel);

    default HotelListResponse hotelListToHotelListResponse(List<Hotel> hotels) {
        HotelListResponse response = new HotelListResponse();
        response.setHotels(hotels.stream()
                .map(this::hotelToResponse).collect(Collectors.toList()));

        return response;
    }
}
