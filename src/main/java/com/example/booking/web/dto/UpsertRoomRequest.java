package com.example.booking.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Range;


@Data
public class UpsertRoomRequest {
    @NotEmpty(message = "Имя должно быть указано")
    private String name;
    @NotEmpty(message = "Описание должно быть указано")
    private String description;
    @Positive(message = " Номер должен быть указан, и быть больше ноля")
    private int number;
    @Positive(message = " Стоимость должна быть указана, и быть больше ноля")
    private int price;
    @Range(min = 1, max = 6, message = "Вместительность может быть быть от {min} до {max}")
    private int maxCapacity;
}