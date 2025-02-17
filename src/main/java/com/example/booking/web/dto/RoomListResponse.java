package com.example.booking.web.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomListResponse {

    private List<RoomResponse> rooms = new ArrayList<>();
}
