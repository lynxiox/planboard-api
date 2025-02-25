package com.example.planboardapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object representing a task response")
public class ResponseTaskDto {
    @Schema(description = "Unique identifier of the task", example = "1")
    Long id;
    @Schema(description = "Title of the task", example = "Updated Task Title")
    String title;
    @Schema(description = "Description of the task", example = "This is an updated description of the task.")
    String description;
    @Schema(description = "Indicates if the task is completed", example = "false")
    Boolean iscompleted;
    @Schema(description = "Creation date of the task", example = "2023-10-11T10:15:30")
    LocalDateTime createdAt;
    @Schema(description = "Completion date of the task", example = "2023-10-12T12:30:45")
    LocalDateTime completedAt;
}
