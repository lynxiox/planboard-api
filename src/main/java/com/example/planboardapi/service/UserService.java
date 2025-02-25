package com.example.planboardapi.service;

import com.example.planboardapi.model.dto.ResponseUserDto;
import com.example.planboardapi.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface UserService extends UserDetailsService {
    void createUser(User user);

    User findByEmail(String email);

    boolean emailIsExists(String email);

    ResponseUserDto getUser(User user);
}
