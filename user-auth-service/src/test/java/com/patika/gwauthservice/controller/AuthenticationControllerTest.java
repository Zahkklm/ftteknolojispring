package com.patika.gwauthservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patika.gwauthservice.dto.LoginUserDto;
import com.patika.gwauthservice.dto.RegisterUserDto;
import com.patika.gwauthservice.entity.User;
import com.patika.gwauthservice.service.AuthenticationService;
import com.patika.gwauthservice.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegister_Success() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("testuser");
        registerUserDto.setPassword("password");

        User user = new User();
        user.setEmail("testuser");

        when(authenticationService.signup(any(RegisterUserDto.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\":\"testuser\"}"));
    }

    @Test
    void testRegister_Failure_UsernameTaken() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("existinguser");
        registerUserDto.setPassword("password");

        when(authenticationService.signup(any(RegisterUserDto.class)))
                .thenThrow(new RuntimeException("Username already taken"));

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Username already taken"));
    }

    @Test
    void testRegister_Failure_BadRequest() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto(); // Missing required fields

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAuthenticate_Success() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("testuser");
        loginUserDto.setPassword("password");

        User user = new User();
        user.setEmail("testuser");

        String token = "jwtToken";
        when(authenticationService.authenticate(any(LoginUserDto.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn(token);
        when(jwtService.getExpirationTime()).thenReturn(3600L);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"jwtToken\",\"expiresIn\":3600}"));
    }

    @Test
    void testAuthenticate_Failure_InvalidCredentials() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("testuser");
        loginUserDto.setPassword("wrongpassword");

        when(authenticationService.authenticate(any(LoginUserDto.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }

    @Test
    void testAuthenticate_Failure_BadRequest() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto(); // Missing required fields

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegister_Failure_MissingFields() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        // Missing fields
        registerUserDto.setEmail("username");

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAuthenticate_Failure_EmptyBody() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Empty JSON object
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAuthenticate_Failure_EmptyUsername() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setPassword("password"); // Missing username

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegister_Failure_InternalServerError() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("testuser");
        registerUserDto.setPassword("password");

        when(authenticationService.signup(any(RegisterUserDto.class)))
                .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error"));
    }

    @Test
    void testAuthenticate_Failure_TokenGenerationError() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("testuser");
        loginUserDto.setPassword("password");

        User user = new User();
        user.setEmail("testuser");

        when(authenticationService.authenticate(any(LoginUserDto.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class)))
                .thenThrow(new RuntimeException("Token generation failed"));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Token generation failed"));
    }

    @Test
    void testRegister_Failure_UsernameTooShort() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("a"); // Too short
        registerUserDto.setPassword("password");

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username must be at least 3 characters"));
    }

    @Test
    void testRegister_Failure_PasswordTooShort() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("testuser");
        registerUserDto.setPassword("pw"); // Too short

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Password must be at least 6 characters"));
    }
}
