package com.example.planboardapi.api;

import com.example.planboardapi.auth.dto.RequestAuthDto;
import com.example.planboardapi.auth.dto.ResponseAuthDto;
import com.example.planboardapi.auth.dto.RequestRefreshDto;
import com.example.planboardapi.auth.dto.RequestRegistrationUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication")
public interface AuthApi {
    @Operation(
            summary = "User registration",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ResponseAuthDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                               "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0I...",
                                                               "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0..."
                                                            }
                                                            """

                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                               "type": "about:blank",
                                                               "title": "Bad Request",
                                                               "status": 400,
                                                               "instance": "/api/v1/auth/registration",
                                                               "errors": [
                                                               "Password size must be between 3 and 20 characters",
                                                               "User's name cannot be blank",
                                                               "Password and confirmation password do not match"
                                                             ]
                                                            }
                                                            """

                                            )
                                    }
                            )
                    )
            }
    )
    @PostMapping("/signup")
    ResponseEntity<ResponseAuthDto> signUp(@RequestBody @Validated RequestRegistrationUserDto registrationUserDto);

    @Operation(
            summary = "User authentication",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseAuthDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                               "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0I...",
                                                               "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0..."
                                                            }
                                                            """

                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                               "type": "about:blank",
                                                               "title": "Bad Request",
                                                               "status": 400,
                                                               "instance": "/api/v1/auth/registration",
                                                               "errors": [
                                                               "Password size must be between 3 and 20 characters",
                                                               "Email must be provided"
                                                             ]
                                                            }
                                                            """

                                            )
                                    }
                            )
                    )
            }
    )
    @PostMapping("/signin")
    ResponseEntity<ResponseAuthDto> signIn(@RequestBody @Validated RequestAuthDto requestDto);

    @Operation(
            summary = "Refresh access token",
            description = "Generates a new access token using the provided refresh token.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully refreshed access token",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseAuthDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                                {
                                                                   "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0I...",
                                                                   "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0..."
                                                                }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Invalid refresh token",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                                {
                                                                   "type": "about:blank",
                                                                   "title": "Unauthorized",
                                                                   "status": 401,
                                                                   "instance": "/api/v1/auth/refresh-token",
                                                                   "errors": [
                                                                   "Refresh token expired or invalid. Please re-authenticate."
                                                                 ]
                                                                }
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    @PostMapping("/refresh-token")
    ResponseEntity<ResponseAuthDto> refreshAccessToken(@RequestBody @Validated RequestRefreshDto requestDto);
}
