package com.example.planboardapi.api;

import com.example.planboardapi.model.dto.RequestCreateTaskDto;
import com.example.planboardapi.model.dto.ResponseTaskDto;
import com.example.planboardapi.model.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Tasks Management")
public interface TasksApi {

    @Operation(
            summary = "Create a new task",
            description = "Creates a new task for the authenticated user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RequestCreateTaskDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Task created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseTaskDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation failed",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                       "type": "about:blank",
                                                       "title": "Bad Request",
                                                       "status": 400,
                                                       "instance": "/api/v1/tasks",
                                                       "errors": [
                                                           "The title must not be empty",
                                                           "The title size must be between 3 and 50 characters"
                                                       ]
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @PostMapping
    ResponseEntity<ResponseTaskDto> createTask(@AuthenticationPrincipal User user,
                                               @Validated @RequestBody RequestCreateTaskDto requestCreateTaskDto);

    @Operation(
            summary = "Get all tasks for the authenticated user",
            description = "Retrieves a list of all tasks associated with the authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of tasks retrieved successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseTaskDto.class)
                            )
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
                                                        "instance": "/api/v1/tasks",
                                                        "errors": [
                                                            "Authentication required"
                                                        ]
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access to the requested resource is forbidden",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "type": "about:blank",
                                                        "title": "Forbidden",
                                                        "status": 403,
                                                        "instance": "/api/v1/tasks",
                                                        "errors": [
                                                            "Access denied"
                                                        ]
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @GetMapping
    ResponseEntity<List<ResponseTaskDto>> getAllTasks(@AuthenticationPrincipal User user);
}