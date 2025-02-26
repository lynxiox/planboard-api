package com.example.planboardapi.controller;

import com.example.planboardapi.api.TaskApi;
import com.example.planboardapi.model.dto.RequestUpdateTaskDto;
import com.example.planboardapi.model.dto.ResponseTaskDto;
import com.example.planboardapi.model.entity.User;
import com.example.planboardapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController implements TaskApi {
    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTaskDto> getTask(@AuthenticationPrincipal User user,
                                                   @PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTask(id, user));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ResponseTaskDto> updateTask(@AuthenticationPrincipal User user,
                                                      @PathVariable Long id,
                                                      @Validated @RequestBody RequestUpdateTaskDto requestUpdateTaskDto) {
        return ResponseEntity.ok(taskService.updateTask(user, id, requestUpdateTaskDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal User user,
                                           @PathVariable Long id) {
        taskService.deleteTask(user, id);
        return ResponseEntity.noContent().build();
    }
}
