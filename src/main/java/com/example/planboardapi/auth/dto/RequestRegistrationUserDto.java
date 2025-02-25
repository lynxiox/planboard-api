package com.example.planboardapi.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(description = "Registration user's request")
public class RequestRegistrationUserDto {
    @Schema(description = "user's name", example = "Ivan")
    @NotNull(message = "User's name must be provided")
    @NotBlank(message = "User's name cannot be blank")
    String username;
    @Schema(description = "user's email", example = "example@mail.com")
    @NotNull(message = "Email must be provided")
    String email;
    @Schema(description = "user's password", example = "password123")
    @NotNull(message = "Password must be provided")
    @Size(min = 3, max = 20, message = "Password size must be between 3 and 20 characters")
    String password;
    @Schema(description = "user's confirm password", example = "password123")
    @NotNull(message = "Password must be provided")
    @Size(min = 3, max = 20, message = "Password size must be between 3 and 20 characters")
    String confirmPassword;
}
