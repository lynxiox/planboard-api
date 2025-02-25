package com.example.planboardapi.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for refreshing access token")
public class RequestRefreshDto {
    @Schema(description = "Refresh token used for generating a new access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0...")
    @NotBlank(message = "The token cannot be empty")
    private String refreshToken;
}
