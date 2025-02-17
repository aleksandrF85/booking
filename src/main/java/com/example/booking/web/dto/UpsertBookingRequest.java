package com.example.booking.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpsertBookingRequest {

    @NotBlank(message = "Название должно быть заполнено")
    private String hotelName;

    @Positive(message = "Номен должен быть заполнен")
    private int roomNumber;

    @FutureOrPresent(message = "Дата должна быть формата ГГГГ-ММ-ДД, сегодня или позднее")
    private LocalDate checkIn;

    @Future(message = "Дата должна быть формата ГГГГ-ММ-ДД и позднее сегодняшнего дня")
    private LocalDate checkOut;

    @Positive(message = "Количество гостей должно быть указано")
    private int guestAmount;

}
