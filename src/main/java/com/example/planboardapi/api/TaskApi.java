package com.example.planboardapi.api;

import com.example.planboardapi.model.dto.RequestUpdateTaskDto;
import com.example.planboardapi.model.dto.ResponseTaskDto;
import com.example.planboardapi.model.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Task Management")
public interface TaskApi {
    @Operation(
            summary = "Get task by ID",
            description = "Retrieves the details of a task by its ID for the authenticated user.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the task to retrieve", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ResponseTaskDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "User not authenticated",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                                {
                                                                   "type": "about:blank",
                                                                   "title": "Unauthorized",
                                                                   "status": 401,
                                                                   "instance": "/api/v1/task/{id}",
                                                                   "errors": [
                                                                       "User not authenticated"
                                                                   ]
                                                                }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                                {
                                                                   "type": "about:blank",
                                                                   "title": "Not Found",
                                                                   "status": 404,
                                                                   "instance": "/api/v1/task/{id}",
                                                                   "errors": [
                                                                       "Task not found"
                                                                   ]
                                                                }
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<ResponseTaskDto> getTask(@AuthenticationPrincipal User user,
                                            @PathVariable Long id);


    @Operation(
            summary = "Update an existing task",
            description = "Updates the details of a task by its ID. Requires authentication.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the task to be updated", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseTaskDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                       "type": "about:blank",
                                                       "title": "Bad Request",
                                                       "status": 400,
                                                       "instance": "/api/v1/task/{id}/update",
                                                       "errors": [
                                                           "The title size must be from 3 to 50 characters",
                                                           "Description size must be up to 1000 characters"
                                                       ]
                                                    }
                                                    """
                                    )
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
                                                       "instance": "/api/v1/task/{id}/update",
                                                       "errors": [
                                                           "User not authenticated"
                                                       ]
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                       "type": "about:blank",
                                                       "title": "Not Found",
                                                       "status": 404,
                                                       "instance": "/api/v1/task/{id}/update",
                                                       "errors": [
                                                           "Task not found"
                                                       ]
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @PatchMapping("{id}/update")
    ResponseEntity<ResponseTaskDto> updateTask(@AuthenticationPrincipal User user,
                                               @PathVariable Long id,
                                               @Validated @RequestBody RequestUpdateTaskDto requestUpdateTaskDto);


    @Operation(
            summary = "Delete a task by its ID",
            description = "Deletes a specific task by its ID. Requires authentication.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the task to be deleted", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Task deleted successfully"
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
                                                   "instance": "/api/v1/task/{id}/delete",
                                                   "errors": [
                                                       "User not authenticated"
                                                   ]
                                                }
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User does not have permission to delete this task",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "type": "about:blank",
                                                   "title": "Forbidden",
                                                   "status": 403,
                                                   "instance": "/api/v1/task/{id}/delete",
                                                   "errors": [
                                                       "You don't have permission to delete this task"
                                                   ]
                                                }
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "type": "about:blank",
                                                   "title": "Not Found",
                                                   "status": 404,
                                                   "instance": "/api/v1/task/{id}/delete",
                                                   "errors": [
                                                       "Task not found"
                                                   ]
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}/delete")
    ResponseEntity<Void> deleteTask(@AuthenticationPrincipal User user,
                                    @PathVariable Long id);
}
