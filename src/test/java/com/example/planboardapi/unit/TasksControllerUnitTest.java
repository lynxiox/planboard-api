package com.example.planboardapi.unit;

import com.example.planboardapi.controller.TasksController;
import com.example.planboardapi.model.dto.RequestCreateTaskDto;
import com.example.planboardapi.model.dto.ResponseTaskDto;
import com.example.planboardapi.model.entity.User;
import com.example.planboardapi.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TasksControllerUnitTest {
    @Mock
    TaskService taskService;

    @InjectMocks
    TasksController tasksController;

    @Test
    void getAllTasks_ReturnsAllTasks() {
        User user = new User();
        List<ResponseTaskDto> tasks = List.of(
                new ResponseTaskDto(1L,
                        "Test title 1",
                        "Test description 1",
                        false,
                        LocalDateTime.of(2024, 1, 1, 1, 1),
                        null),
                new ResponseTaskDto(1L,
                        "Test title 2",
                        "Test description 2",
                        false,
                        LocalDateTime.of(2024, 2, 1, 1, 1),
                        null)
        );
        when(taskService.getAllTasks(user))
                .thenReturn(tasks);

        ResponseEntity<List<ResponseTaskDto>> actualResult = tasksController.getAllTasks(user);

        assertNotNull(actualResult);
        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
        assertEquals(tasks, actualResult.getBody());
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void createTask_ReturnsTask() {
        User user = new User();
        RequestCreateTaskDto createTaskDto = new RequestCreateTaskDto("   Test title  ", "Test description");

        when(taskService.save(user, createTaskDto))
                .thenReturn(new ResponseTaskDto(1L,
                        "Test title",
                        "Test description",
                        false,
                        LocalDateTime.of(2024, 1, 1, 1, 1),
                        null));

        ResponseEntity<ResponseTaskDto> actualResult = tasksController.createTask(user, createTaskDto);

        assertNotNull(actualResult);
        assertEquals(HttpStatus.CREATED, actualResult.getStatusCode());
        assertEquals(new ResponseTaskDto(1L,
                        "Test title",
                        "Test description",
                        false,
                        LocalDateTime.of(2024, 1, 1, 1, 1),
                        null),
                actualResult.getBody());
        verifyNoMoreInteractions(taskService);
    }
}
