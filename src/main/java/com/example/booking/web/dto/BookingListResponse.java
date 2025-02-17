package com.example.booking.web.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookingListResponse {

    private List<BookingResponse> bookingList = new ArrayList<>();
}
