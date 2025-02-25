package com.example.planboardapi.controller;

import com.example.planboardapi.api.TasksApi;
import com.example.planboardapi.model.dto.RequestCreateTaskDto;
import com.example.planboardapi.model.dto.ResponseTaskDto;
import com.example.planboardapi.model.entity.User;
import com.example.planboardapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TasksController implements TasksApi {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ResponseTaskDto> createTask(@AuthenticationPrincipal User user,
                                                      @Validated @RequestBody RequestCreateTaskDto requestCreateTaskDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.save(user, requestCreateTaskDto));
    }

    @GetMapping
    public ResponseEntity<List<ResponseTaskDto>> getAllTasks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getAllTasks(user));
    }
}
