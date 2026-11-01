package com.HealthApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService service;

    @BeforeEach
    void setUp() {
        service = new JwtService();

        byte[] keyBytes = new byte[64];
        java.util.Arrays.fill(keyBytes, (byte) 42);

        String testSecret = Base64.getEncoder()
                .encodeToString(keyBytes);

        ReflectionTestUtils.setField(service, "secretKey", testSecret);
    }

    @Test
    void testGenerateToken_whenUsernameProvided_returnAStringToken() {
        // Arrange
        String username = "admin@example.com";

        // Act
        String token = service.generateToken(username);

        // Assert
        assertEquals(username, service.extractUsername(token));
    }

    @Test
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
}
