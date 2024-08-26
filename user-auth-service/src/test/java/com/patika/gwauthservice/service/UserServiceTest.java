package com.patika.gwauthservice.service;


import com.patika.gwauthservice.entity.User;
import com.patika.gwauthservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllUsers_Success() {
        // Given
        User user1 = new User();
        user1.setEmail("test1@example.com");
        User user2 = new User();
        user2.setEmail("test2@example.com");
        List<User> userList = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        // When
        List<User> result = userService.allUsers();

        // Then
        assertEquals(2, result.size());
        assertEquals(user1, result.get(0));
        assertEquals(user2, result.get(1));
    }

    @Test
    void testAllUsers_Empty() {
        // Given
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        List<User> result = userService.allUsers();

        // Then
        assertEquals(0, result.size());
    }

    @Test
    void testAllUsers_SingleUser() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        List<User> userList = List.of(user);

        when(userRepository.findAll()).thenReturn(userList);

        // When
        List<User> result = userService.allUsers();

        // Then
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }
}

