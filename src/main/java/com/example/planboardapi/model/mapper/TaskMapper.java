package com.example.planboardapi.model.mapper;

import com.example.planboardapi.model.dto.RequestCreateTaskDto;
import com.example.planboardapi.model.dto.ResponseTaskDto;
import com.example.planboardapi.model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "iscompleted", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Task requestTaskCreateDtoToTask(RequestCreateTaskDto requestCreateTaskDto);

    @Mapping(source = "iscompleted", target = "iscompleted")
    ResponseTaskDto taskToResponseTaskCreateDto(Task task);
}
