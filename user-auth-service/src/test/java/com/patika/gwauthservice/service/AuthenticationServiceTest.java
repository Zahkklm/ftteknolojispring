package com.patika.gwauthservice.service;

import com.patika.gwauthservice.dto.LoginUserDto;
import com.patika.gwauthservice.dto.RegisterUserDto;
import com.patika.gwauthservice.entity.User;
import com.patika.gwauthservice.entity.enums.Role;
import com.patika.gwauthservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup_Success() {
        // Given
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setFullName("Test User");
        registerUserDto.setEmail("test@example.com");
        registerUserDto.setPassword("password");

        User user = new User();
        user.setFullName(registerUserDto.getFullName());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword("encodedPassword");
        user.setRole(Role.ADMIN);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User result = authenticationService.signup(registerUserDto);

        // Then
        assertNotNull(result);
        assertEquals("Test User", result.getFullName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals(Role.ADMIN, result.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSignup_Failure_EmailAlreadyExists() {
        // Given
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setFullName("Test User");
        registerUserDto.setEmail("test@example.com");
        registerUserDto.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        // When & Then
        assertThrows(RuntimeException.class, () -> authenticationService.signup(registerUserDto));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testAuthenticate_Success() {
        // Given
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("test@example.com");
        loginUserDto.setPassword("password");

        User user = new User();
        user.setEmail(loginUserDto.getEmail());

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // When
        User result = authenticationService.authenticate(loginUserDto);

        // Then
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testAuthenticate_Failure_BadCredentials() {
        // Given
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("test@example.com");
        loginUserDto.setPassword("wrongpassword");

        doThrow(new BadCredentialsException("Bad credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // When & Then
        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(loginUserDto));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testAuthenticate_Failure_UserNotFound() {
        // Given
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("test@example.com");
        loginUserDto.setPassword("password");

        doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(loginUserDto));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testAllUsers_Success() {
        // Given
        User user1 = new User();
        user1.setEmail("user1@example.com");
        User user2 = new User();
        user2.setEmail("user2@example.com");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> result = authenticationService.allUsers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1@example.com", result.get(0).getEmail());
        assertEquals("user2@example.com", result.get(1).getEmail());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testAllUsers_Empty() {
        // Given
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        List<User> result = authenticationService.allUsers();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testAllUsers_SingleUser() {
        // Given
        User user = new User();
        user.setEmail("singleuser@example.com");
        List<User> users = List.of(user);

        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> result = authenticationService.allUsers();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("singleuser@example.com", result.get(0).getEmail());
    }
}
