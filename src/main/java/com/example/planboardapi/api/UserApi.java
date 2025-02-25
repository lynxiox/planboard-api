package com.example.planboardapi.api;

import com.example.planboardapi.model.dto.ResponseUserDto;
import com.example.planboardapi.model.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "User Management")
public interface UserApi {

    @Operation(
            summary = "Get user information",
            description = "Retrieves information about the authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User information retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ResponseUserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "User not authenticated",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "type": "about:blank",
                                                        "title": "Unauthorized",
                                                        "status": 401,
                                                        "instance": "/api/v1/users",
                                                        "errors": [
                                                            "Authentication required"
                                                        ]
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "type": "about:blank",
                                                        "title": "Not Found",
                                                        "status": 404,
                                                        "instance": "/api/v1/users",
                                                        "errors": [
                                                            "User not found"
                                                        ]
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @GetMapping
    ResponseEntity<ResponseUserDto> getUser(User user);
}

