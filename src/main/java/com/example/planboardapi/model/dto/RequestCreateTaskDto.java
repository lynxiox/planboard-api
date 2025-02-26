package com.example.planboardapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "DTO for creating a new task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreateTaskDto {
    @Schema(description = "Title of the task", example = "Complete project report")
    @NotBlank(message = "The title must not be empty")
    @Size(min = 3, max = 50, message = "The title size must be between 3 and 50 characters")
    private String title;
    @Schema(description = "Description of the task, can be empty", example = "Finish writing the report by the end of the day")
    @Size(max = 1000, message = "The description size must be up to 1000 characters")
    private String description;
}
