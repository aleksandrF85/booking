package com.example.booking.web.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class UpsertRatingRequest {

    @Range(min = 1, max = 5, message = "Оценка должна быть от {min} до {max}")
    int mark;
}
