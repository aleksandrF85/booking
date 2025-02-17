package com.example.booking.service;

import com.example.booking.model.event.RoomBookingEvent;
import com.example.booking.model.event.UserRegistrationEvent;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface StatisticsService {

    void collectUserRegistrationStatistics(UserRegistrationEvent event);

    void collectRoomBookingStatistics(RoomBookingEvent event);

    String recordStatisticsToCSV() throws IOException;

    public Resource downloadStatistics();
}
