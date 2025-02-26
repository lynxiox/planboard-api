package com.example.planboardapi.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Access token response")
public class ResponseAuthDto {
    @Schema(description = "accessToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0I...")
    private String accessToken;
    @Schema(description = "refreshToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0...")
    private String refreshToken;
}
