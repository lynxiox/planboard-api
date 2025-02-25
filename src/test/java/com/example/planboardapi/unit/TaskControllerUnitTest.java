package com.example.planboardapi.unit;

import com.example.planboardapi.controller.TaskController;
import com.example.planboardapi.model.dto.RequestUpdateTaskDto;
import com.example.planboardapi.model.dto.ResponseTaskDto;
import com.example.planboardapi.model.entity.User;
import com.example.planboardapi.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerUnitTest {

    @Mock
    TaskService taskService;
    @InjectMocks
    TaskController taskController;

    private User user;
    private Long taskId;

    @BeforeEach
    void setUp() {
        user = new User();
        taskId = 1L;
    }

    @Test
    void getTask_ReturnsTask() {
        ResponseTaskDto responseTaskDto = new ResponseTaskDto(1L,
                "Test title 1",
                "Test description 1",
                false,
                LocalDateTime.of(2024, 1, 1, 1, 1),
                null);

        when(taskService.getTask(taskId, user))
                .thenReturn(responseTaskDto);

        ResponseEntity<ResponseTaskDto> actualResult = taskController.getTask(user, taskId);

        assertNotNull(actualResult);
        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
        assertEquals(responseTaskDto, actualResult.getBody());
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void updateTask_whenValidInput_thenReturnUpdatedTask() {
        RequestUpdateTaskDto requestUpdateTaskDto = new RequestUpdateTaskDto(
                "Test title 1",
                "Test description 1",
                false);
        ResponseTaskDto responseTaskDto = new ResponseTaskDto(1L,
                "Test title 1",
                "Test description 1",
                false,
                LocalDateTime.of(2024, 1, 1, 1, 1),
                null);

        when(taskService.updateTask(user, taskId, requestUpdateTaskDto))
                .thenReturn(responseTaskDto);

        ResponseEntity<ResponseTaskDto> actualResult = taskController.updateTask(user, taskId, requestUpdateTaskDto);

        assertNotNull(actualResult);
        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
        assertEquals(responseTaskDto, actualResult.getBody());
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void testDeleteTask_whenValidInput_thenReturnOk() {
        doNothing().when(taskService).deleteTask(user, taskId);

        taskService.deleteTask(user, taskId);
        verify(taskService, times(1)).deleteTask(user, taskId);
    }
}
