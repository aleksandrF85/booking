package com.example.booking.service.impl;


import com.example.booking.model.EventType;
import com.example.booking.model.Statistic;
import com.example.booking.model.event.RoomBookingEvent;
import com.example.booking.model.event.UserRegistrationEvent;
import com.example.booking.repository.StatisticRepository;
import com.example.booking.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseStatisticsService implements StatisticsService {

    @Value("${app.kafka.roomBookings}")
    private String roomBookingsTopic;

    @Value("${app.kafka.userRegistrations}")
    private String userRegistrationsTopic;

    @Value("${app.kafka.groupId}")
    private String groupId;

    private final StatisticRepository statisticRepository;

    @KafkaListener(topics = "${app.kafka.userRegistrations}",
            groupId = "${app.kafka.groupId}",
            containerFactory = "userRegistrationEventKafkaListenerContainerFactory")
    @Override
    public void collectUserRegistrationStatistics(UserRegistrationEvent event) {
        Statistic statistic = new Statistic();
        statistic.setUserId(event.getUserId());
        statistic.setEventType(EventType.USER_REGISTRATION);
        statistic.setEventDateAndTime(event.getEventDateTime());
        statisticRepository.save(statistic);
    }


    @KafkaListener(topics = "${app.kafka.roomBookings}",
            groupId = "${app.kafka.groupId}",
            containerFactory = "roomBookingEventKafkaListenerContainerFactory")
    @Override
    public void collectRoomBookingStatistics(RoomBookingEvent event) {
        Statistic statistic = new Statistic();
        statistic.setUserId(event.getUserId());
        statistic.setRoomId(event.getRoomId());
        statistic.setCheckInDate(event.getCheckInDate());
        statistic.setCheckOutDate(event.getCheckOutDate());
        statistic.setEventType(EventType.ROOM_BOOKING);
        statistic.setEventDateAndTime(event.getEventDateTime());
        statisticRepository.save(statistic);
    }

    @Override
    public String recordStatisticsToCSV() throws IOException {

        List<Statistic> statistics = statisticRepository.findAll();
        StringBuilder csvBuilder = new StringBuilder();

        // Добавляем заголовки CSV
        csvBuilder.append("User ID, Room ID, Check In Date, Check Out Date, Event Type, Event DateTime\n");

        // Заполняем строки данными
        for (Statistic statistic : statistics) {
            csvBuilder.append(statistic.getUserId() != null ? statistic.getUserId().toString() : "-")
                    .append(",")
                    .append(statistic.getRoomId() != null ? statistic.getRoomId().toString() : "-")
                    .append(",")
                    .append(statistic.getCheckInDate() != null ? statistic.getCheckInDate().toString() : "-")
                    .append(",")
                    .append(statistic.getCheckOutDate() != null ? statistic.getCheckOutDate().toString() : "-")
                    .append(",")
                    .append(statistic.getEventType() != null ? statistic.getEventType().toString() : "-")
                    .append(",")
                    .append(statistic.getEventDateAndTime() != null ? statistic.getEventDateAndTime().toString() : "-")
                    .append("\n");
        }

        // Возвращаем данные CSV как строку
        return csvBuilder.toString();
    }

    @Override
    public Resource downloadStatistics() {

        try {
            // Получаем данные в формате CSV
            String csvData = recordStatisticsToCSV();

            // Преобразуем строку в InputStreamResource
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(csvData.getBytes());
            InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

            return resource;

        } catch (IOException e) {

            return new InputStreamResource(new ByteArrayInputStream("Ошибка записи файла CSV".getBytes()));
        }
    }
}
