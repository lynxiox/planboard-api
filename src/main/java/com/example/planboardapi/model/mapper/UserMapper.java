package com.example.planboardapi.model.mapper;

import com.example.planboardapi.auth.dto.RequestRegistrationUserDto;
import com.example.planboardapi.model.dto.ResponseUserDto;
import com.example.planboardapi.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(RequestRegistrationUserDto registrationUserDto);

    @Mapping(source = "name", target = "username")
    ResponseUserDto toResponseUserDto(User user);
}