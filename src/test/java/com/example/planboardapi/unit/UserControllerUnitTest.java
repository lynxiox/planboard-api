package com.example.planboardapi.unit;

import com.example.planboardapi.controller.UserController;
import com.example.planboardapi.exception.custom.UserNotFoundException;
import com.example.planboardapi.model.dto.ResponseUserDto;
import com.example.planboardapi.model.entity.User;
import com.example.planboardapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {
    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;

    @Test
    void getUser_whenValidUser_thenReturnResponseUserDto(){
        User user = new User();
        user.setId(1L);
        user.setUsername("Test user");
        user.setEmail("testUser@example.com");

        ResponseUserDto responseUserDto = new ResponseUserDto(1L, "Test user", "testUser@example.com");

        when(userService.getUser(user))
                .thenReturn(responseUserDto);

        ResponseEntity<ResponseUserDto> actualResult = userController.getUser(user);

        assertNotNull(actualResult);
        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
        assertEquals(responseUserDto, actualResult.getBody());

        verifyNoMoreInteractions(userService);
    }

    @Test
    void getUser_whenInvalidUser_thenThrowException(){
        User user = new User();
        user.setEmail("");

        when(userService.getUser(user))
                .thenThrow(new UserNotFoundException(user.getEmail()));

        Exception exception = assertThrows(UserNotFoundException.class, () -> userController.getUser(user));

        assertEquals("User with email - " + user.getEmail() + " not found", exception.getMessage());
        verify(userService).getUser(user);
    }


}
