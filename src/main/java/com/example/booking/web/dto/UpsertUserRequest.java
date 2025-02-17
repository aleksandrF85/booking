package com.example.booking.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpsertUserRequest {
    @NotBlank(message = "Имя должно быть указано")
    @Size(min = 5, max = 20, message = "Имя не должно быть меньше {min} и больше {max} символов!")
    private String username;

    @NotBlank(message = " Email должен быть указан")
    private String email;

    @NotBlank(message = "Пароль должен быть указан")
    @Size(min = 4, max = 10, message = "Пароль не должен быть меньше {min} и больше {max} символов!")
    private String password;
}
