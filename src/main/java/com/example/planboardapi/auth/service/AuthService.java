package com.example.planboardapi.auth.service;
import com.example.planboardapi.auth.dto.RequestAuthDto;
import com.example.planboardapi.auth.dto.RequestRegistrationUserDto;
import com.example.planboardapi.auth.dto.ResponseAuthDto;
import com.example.planboardapi.auth.dto.RequestRefreshDto;
import com.example.planboardapi.exception.custom.AuthenticationFailedException;
import com.example.planboardapi.exception.custom.EmailAlreadyExistsException;
import com.example.planboardapi.exception.custom.InvalidTokenException;
import com.example.planboardapi.exception.custom.PasswordMismatchException;
import com.example.planboardapi.kafka.dto.EmailSendingDto;
import com.example.planboardapi.model.entity.Role;
import com.example.planboardapi.model.entity.User;
import com.example.planboardapi.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.planboardapi.service.UserService;
import com.example.planboardapi.kafka.service.KafkaService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final KafkaService kafkaService;

    public ResponseAuthDto signup(RequestRegistrationUserDto registrationUserDto) {
        if (!(registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword()))) {
            throw new PasswordMismatchException();
        }
        String email = registrationUserDto.getEmail();
        if (userService.emailIsExists(email)) {
            throw new EmailAlreadyExistsException(email);
        }
        User user = userMapper.toUser(registrationUserDto);
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRole(Role.USER);

        userService.createUser(user);
        sendKafkaMessage(user);
        log.info("Create user with username - {} and email - {}", user.getName(), user.getEmail());

        String refreshToken = jwtService.generateToken(user, false);

        return new ResponseAuthDto(
                jwtService.generateToken(user, true),
                refreshToken
        );
    }

    public ResponseAuthDto signin(RequestAuthDto requestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Incorrect email or password");
        }
        User user = userService.findByEmail(requestDto.getEmail());

        String refreshToken = jwtService.generateToken(user, false);

        return new ResponseAuthDto(
                jwtService.generateToken(user, true),
                refreshToken
        );
    }

    public ResponseAuthDto refreshToken(RequestRefreshDto requestDto) {
        try {
            String refreshToken = requestDto.getRefreshToken();
            String email = jwtService.extractUsername(refreshToken, false);
            User user = userService.findByEmail(email);

            if (!jwtService.isValid(refreshToken, user, false)) {
                throw new InvalidTokenException("Invalid refresh token");
            }

            String accessToken = jwtService.generateToken(user, true);
            return new ResponseAuthDto(accessToken, refreshToken);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    private void sendKafkaMessage(User user) {
        EmailSendingDto emailSendingDto = new EmailSendingDto(
                user.getEmail(),
                "Thank you for registration!",
                "Hello " + user.getName() + "!" + " "
        );
        kafkaService.sendRegisterEmailMessage(emailSendingDto);
        log.info("EmailSendingDto: {}", emailSendingDto);
    }
}
