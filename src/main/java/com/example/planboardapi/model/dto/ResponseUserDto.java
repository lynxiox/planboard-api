package com.example.planboardapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO representing a user")
public class ResponseUserDto {
    @Schema(description = "Unique identifier of the user", example = "42")
    Long id;
    @Schema(description = "Username of the user", example = "Ivan")
    String username;
    @Schema(description = "Email address of the user", example = "mail@example.com")
    String email;
}
