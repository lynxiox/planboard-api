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
    private Long id;
    @Schema(description = "Title of the task", example = "Updated Task Title")
    private String title;
    @Schema(description = "Description of the task", example = "This is an updated description of the task.")
    private String description;
    @Schema(description = "Indicates if the task is completed", example = "false")
    private Boolean iscompleted;
    @Schema(description = "Creation date of the task", example = "2023-10-11T10:15:30")
    private LocalDateTime createdAt;
    @Schema(description = "Completion date of the task", example = "2023-10-12T12:30:45")
    private LocalDateTime completedAt;
}
