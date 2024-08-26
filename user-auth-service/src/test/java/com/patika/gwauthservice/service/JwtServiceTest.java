package com.patika.gwauthservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService.secretKey = "your-secret-key-in-base64-format";
        jwtService.jwtExpiration = 1000L * 60 * 60; // 1 hour expiration for testing
    }

    @Test
    void testGenerateToken() {
        // Given
        when(userDetails.getUsername()).thenReturn("testUser");

        // When
        String token = jwtService.generateToken(userDetails);

        // Then
        assertNotNull(token);
        assertTrue(token.startsWith("eyJ")); // Basic check for JWT token format
    }

    @Test
    void testGenerateTokenWithClaims() {
        // Given
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "USER");
        when(userDetails.getUsername()).thenReturn("testUser");

        // When
        String token = jwtService.generateToken(extraClaims, userDetails);

        // Then
        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void testExtractUsername() {
        // Given
        String token = jwtService.generateToken(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        // When
        String username = jwtService.extractUsername(token);

        // Then
        assertEquals("testUser", username);
    }

    @Test
    void testExtractClaim() {
        // Given
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");
        String token = jwtService.buildToken(claims, userDetails, jwtService.getExpirationTime());

        // When
        String role = jwtService.extractClaim(token, claimsMap -> claimsMap.get("role").toString());

        // Then
        assertEquals("USER", role);
    }

    @Test
    void testIsTokenValid() {
        // Given
        String token = jwtService.generateToken(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        // When
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Then
        assertTrue(isValid);
    }

    @Test
    void testIsTokenExpired() {
        // Given
        String token = jwtService.generateToken(userDetails);
        jwtService.jwtExpiration = -1000L; // Set expiration time to a negative value to simulate an expired token

        // When
        boolean isExpired = jwtService.isTokenExpired(token);

        // Then
        assertTrue(isExpired);
    }

    @Test
    void testExtractExpiration() {
        // Given
        String token = jwtService.generateToken(userDetails);

        // When
        Date expiration = jwtService.extractExpiration(token);

        // Then
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void testExtractAllClaims() {
        // Given
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");
        String token = jwtService.buildToken(claims, userDetails, jwtService.getExpirationTime());

        // When
        Claims allClaims = jwtService.extractAllClaims(token);

        // Then
        assertEquals("USER", allClaims.get("role"));
    }

    @Test
    void testGetSignInKey() {
        // Given
        Key key = Keys.hmacShaKeyFor(jwtService.secretKey.getBytes());

        // When
        Key resultKey = jwtService.getSignInKey();

        // Then
        assertEquals(key, resultKey);
    }

    @Test
    void testExceptionHandlingForInvalidToken() {
        // Given
        String invalidToken = "invalidToken";

        // When & Then
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> {
            jwtService.extractAllClaims(invalidToken);
        });
    }

    @Test
    void testExceptionHandlingForMalformedToken() {
        // Given
        String malformedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"; // Incomplete token

        // When & Then
        assertThrows(io.jsonwebtoken.MalformedJwtException.class, () -> {
            jwtService.extractAllClaims(malformedToken);
        });
    }

    @Test
    void testExceptionHandlingForInvalidSecretKey() {
        // Given
        jwtService.secretKey = "invalid-key";
        String token = jwtService.generateToken(userDetails);

        // When & Then
        assertThrows(io.jsonwebtoken.SignatureException.class, () -> {
            jwtService.extractAllClaims(token);
        });
    }

    @Test
    void testNullClaimsExtraction() {
        // Given
        String token = jwtService.generateToken(userDetails);

        // When
        String claim = jwtService.extractClaim(token, claimsMap -> claimsMap.get("nonexistentClaim")).toString();

        // Then
        assertNull(claim);
    }
}
