package com.example.booking.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "statistics")
public class Statistic {

    @Id
    private String id;

    private Long userId;

    private Long roomId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private EventType eventType;

    private LocalDateTime eventDateAndTime;
}
