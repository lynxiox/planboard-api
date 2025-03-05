package com.example.planboardapi.service.impl;

import com.example.planboardapi.exception.custom.TaskAlreadyExistsException;
import com.example.planboardapi.exception.custom.TaskNotFoundException;
import com.example.planboardapi.model.dto.RequestCreateTaskDto;
import com.example.planboardapi.model.dto.RequestUpdateTaskDto;
import com.example.planboardapi.model.dto.ResponseTaskDto;
import com.example.planboardapi.model.entity.Task;
import com.example.planboardapi.model.entity.User;
import com.example.planboardapi.model.mapper.TaskMapper;
import com.example.planboardapi.model.repository.TaskRepository;
import com.example.planboardapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    @Override
    public ResponseTaskDto save(User user, RequestCreateTaskDto requestCreateTaskDto) {
        String normalizedTitle = normalizeTitle(requestCreateTaskDto.getTitle());

        if (taskRepository.existsByTitleAndUser(normalizedTitle, user)) {
            throw new TaskAlreadyExistsException("Task with this title already exists for this user");
        }

        Task task = taskMapper.requestTaskCreateDtoToTask(requestCreateTaskDto);
        task.setTitle(normalizedTitle);
        task.setUser(user);
        return taskMapper.taskToResponseTaskCreateDto(taskRepository.save(task));
    }

    @Override
    public List<ResponseTaskDto> getAllTasks(User user) {
        List<ResponseTaskDto> tasks = taskRepository
                .findByUser(user)
                .stream()
                .map(taskMapper::taskToResponseTaskCreateDto)
                .toList();
        return tasks;
    }

    @Override
    public ResponseTaskDto getTask(Long id, User user) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return taskMapper.taskToResponseTaskCreateDto(task);
    }

    @Transactional
    @Override
    public ResponseTaskDto updateTask(User user, Long id, RequestUpdateTaskDto requestUpdateTaskDto) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (StringUtils.hasText(requestUpdateTaskDto.getTitle()) &&
                !requestUpdateTaskDto.getTitle().equals(task.getTitle()) &&
                taskRepository.existsByTitleAndUser(requestUpdateTaskDto.getTitle(), user)) {
            throw new TaskAlreadyExistsException("Task with this title already exists for this user");
        }

        updateTaskFields(task, requestUpdateTaskDto);

        taskRepository.save(task);

        return taskMapper.taskToResponseTaskCreateDto(task);
    }

    @Transactional
    @Override
    public void deleteTask(User user, Long id) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    private void updateTaskFields(Task task, RequestUpdateTaskDto dto) {
        if (dto.getTitle() != null && !Objects.equals(task.getTitle(), dto.getTitle())) {
            task.setTitle(normalizeTitle(dto.getTitle()));
        }
        if (dto.getDescription() != null && !Objects.equals(task.getDescription(), dto.getDescription())) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getIscompleted() != null) {
            task.setIscompleted(dto.getIscompleted());
            task.setCompletedAt(dto.getIscompleted() ? LocalDateTime.now() : null);
        }
    }

    private String normalizeTitle(String title) {
        return title == null ? null : title.trim();
    }
}
