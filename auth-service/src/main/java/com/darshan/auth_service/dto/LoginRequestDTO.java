package com.darshan.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotNull(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "password is required")
    @Size(message = "password must be 8 char long")
    private String password;

}
