package com.HealthApp.service;

import com.HealthApp.exception.InvalidRefreshTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService service;
    private SecretKey testKey;

    @BeforeEach
    void setUp() {
        service = new JwtService();

        byte[] keyBytes = new byte[64];
        java.util.Arrays.fill(keyBytes, (byte) 42);

        String testSecret = Base64.getEncoder()
                .encodeToString(keyBytes);

        ReflectionTestUtils.setField(service, "secretKey", testSecret);

        testKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    @DisplayName("test generate token")
    void testGenerateToken_whenUsernameProvided_returnAStringToken() {
        // Arrange
        String username = "admin@example.com";

        // Act
        String token = service.generateToken(username);

        // Assert
        assertEquals(username, service.extractUsername(token));
    }

    @Test
    @DisplayName("Test generate refresh token")
    void testGenerateRefreshToken_whenSessionIdTokenIdExpirationProvided_returnRefreshTokenString() {
        // Arrange
        UUID sessionId = UUID.randomUUID();
        UUID tokenId = UUID.randomUUID();
        Instant expiresAt = Instant.now().plus(7, ChronoUnit.DAYS);


        // Act
        String token = service.generateRefreshToken(sessionId, tokenId, expiresAt);

        // Assert
        assertEquals(sessionId, service.extractSessionId(token));
        assertEquals(tokenId, service.extractTokenId(token));

        assertEquals(expiresAt.getEpochSecond(), service.extractAllClaims(token)
                .getExpiration()
                .toInstant()
                .getEpochSecond());
    }

    @Test
    @DisplayName("Test extract session id throw invalid refresh token exception")

    // Create a valid, signed JWT containing an invalid UUID string
    void testExtractSessionId_whenInvalidSessionUUID_throwException() {
        // Arrange
        String token = Jwts.builder()
                .claim("sessionId", "invalid-uuid")
                .signWith(testKey)
                .compact();

        // Act and Assert
        assertThrows(InvalidRefreshTokenException.class, () -> service.extractSessionId(token));
    }

    @Test
    @DisplayName("Test extract token id throw invalid refresh token exception")

    // Create a valid, signed JWT containing an invalid UUID string
    void testExtractTokenId_whenInvalidTokenUUID_throwException() {
        // Arrange
        String token = Jwts.builder()
                .claim("tokenId", "invalid-uuid")
                .signWith(testKey)
                .compact();

        // Act and Assert
        assertThrows(InvalidRefreshTokenException.class, () -> service.extractTokenId(token));
    }
}
