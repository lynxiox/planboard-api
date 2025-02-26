package com.example.planboardapi.service.impl;

import com.example.planboardapi.model.mapper.UserMapper;
import com.example.planboardapi.model.dto.ResponseUserDto;
import com.example.planboardapi.model.entity.User;
import com.example.planboardapi.model.repository.UserRepository;
import com.example.planboardapi.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public boolean emailIsExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public ResponseUserDto getUser(User user) {
        ResponseUserDto responseUser = userMapper.toResponseUserDto(findByEmail(user.getEmail()));
        log.info("Getting user with username {} and password {}", user.getEmail(), user.getPassword());
        return responseUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }
}
