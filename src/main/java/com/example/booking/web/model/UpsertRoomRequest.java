package com.example.booking.web.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Range;


@Data
public class UpsertRoomRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @Positive
    private int number;
    @Positive
    private int price;
    @Range(min = 1, max = 6)
    private int maxCapacity;
}