package com.example.booking.web.model;

import com.example.booking.model.Room;
import com.example.booking.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UpsertBookingRequest {

    @NotBlank(message = "Название должно быть заполнено")
    private String hotelName;

    @Positive(message = "Номен должен быть заполнен")
    private int roomNumber;

    @FutureOrPresent
    private LocalDate checkIn;

    @Future
    private LocalDate checkOut;

    @Positive(message = "Количество гостей должно быть указано")
    private int guestAmount;

}
