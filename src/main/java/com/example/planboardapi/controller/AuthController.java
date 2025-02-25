package com.example.planboardapi.controller;

import com.example.planboardapi.api.AuthApi;
import com.example.planboardapi.auth.dto.RequestAuthDto;
import com.example.planboardapi.auth.dto.ResponseAuthDto;
import com.example.planboardapi.auth.dto.RequestRefreshDto;
import com.example.planboardapi.auth.dto.RequestRegistrationUserDto;
import com.example.planboardapi.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseAuthDto> signUp(@RequestBody @Validated RequestRegistrationUserDto registrationUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(registrationUserDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseAuthDto> signIn(@RequestBody @Validated RequestAuthDto requestDto) {
        return ResponseEntity.ok(authService.signin(requestDto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseAuthDto> refreshAccessToken(@RequestBody @Validated RequestRefreshDto requestDto) {
        return ResponseEntity.ok(authService.refreshToken(requestDto));
    }
}
