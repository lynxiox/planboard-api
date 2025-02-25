package com.example.planboardapi.controller;

import com.example.planboardapi.api.UserApi;
import com.example.planboardapi.model.dto.ResponseUserDto;
import com.example.planboardapi.model.entity.User;
import com.example.planboardapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController implements UserApi {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResponseUserDto> getUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUser(user));
    }
}
