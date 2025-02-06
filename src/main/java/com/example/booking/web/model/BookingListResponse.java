package com.example.booking.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookingListResponse {

    private List<BookingResponse> bookingList = new ArrayList<>();
}
