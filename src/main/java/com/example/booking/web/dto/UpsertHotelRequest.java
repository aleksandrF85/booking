package com.example.booking.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class UpsertHotelRequest {
    @NotBlank(message = "Название должно быть заполнено")
    private String name;
    @NotBlank(message = "Заголовок объявления должен быть заполнен")
    private String listingTitle;
    @NotBlank(message = "Город должен быть указан")
    private String city;
    @NotBlank(message = "Адрес должен быть указан")
    private String address;
    @Positive(message = "Расстояние до центра города должно быть больше ноля")
    private double distanceFromTheCityCenter;
}