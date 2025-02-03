package com.example.booking.web.model;

import com.example.booking.web.model.RoomResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomListResponse {

    private List<RoomResponse> rooms = new ArrayList<>();
}
