package com.HealthApp.service;

import com.HealthApp.model.RefreshSession;
import com.HealthApp.model.RevocationReason;
import com.HealthApp.model.Role;
import com.HealthApp.repo.RefreshSessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshSessionServiceTest {

    @Mock
    private RefreshSessionRepository repo;

    @InjectMocks
    private RefreshSessionService service;

    @Test
    @DisplayName("Test create session with ADMIN role")
    void testCreateSession_whenUserIdAndAdminProvided_returnRefreshSessionObject() {
        // Arrange
        when(repo.save(any(RefreshSession.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        RefreshSession s = service.createSession(1L, Role.ADMIN);

        // Assert
        assertEquals(1L, s.getUserId());
        assertEquals(Role.ADMIN, s.getRole());
        assertFalse(s.isRevoked());

        /* Execution flow:
        - service.createSession(1L, Role.ADMIN) creates a new RefreshSession
        - service call save()
        - Mockito intercepts the call
        - invocation.getArgument(0) retrieves the session passed into save()
        - Mockito returns that save object to the service
        - The service returns it to the test as s
         */
    }

    @Test
    @DisplayName("Test create session with CLIENT role")
    void testCreateSession_whenUserIdAndClientProvided_returnRefreshSessionObject() {
        // Arrange
        when(repo.save(any(RefreshSession.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        RefreshSession s = service.createSession(2L, Role.CLIENT);

        // Assert
        assertEquals(2L, s.getUserId());
        assertEquals(Role.CLIENT, s.getRole());
        assertFalse(s.isRevoked());
    }

    @Test
    @DisplayName("Test create session with COACH role")
    void testCreateSession_whenUserIdAndCoachProvided_returnRefreshSessionObject() {
        // Arrange
        when(repo.save(any(RefreshSession.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        RefreshSession s = service.createSession(3L, Role.COACH);

        // Assert
        assertEquals(3L, s.getUserId());
        assertEquals(Role.COACH, s.getRole());
        assertFalse(s.isRevoked());
    }

    @Test
    @DisplayName("Test refresh session")
    void testRefreshSession_whenSessionIdAndTokenIdProvided_returnRefreshSessionObject() {
        // Arrange
        UUID sessionId = UUID.randomUUID();
        UUID tokenId = UUID.randomUUID();

        RefreshSession session = new RefreshSession();

        session.setSessionId(sessionId);
        session.setCurrentTokenId(tokenId);
        session.setRevoked(false);
        session.setExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS));

        when(repo.findBySessionId(sessionId))
                .thenReturn(Optional.of(session));

        when(repo.save(session)).thenReturn(session);

        // Act
        RefreshSession s = service.refreshSession(sessionId, tokenId);

        // Assert
        assertEquals(sessionId, s.getSessionId());
        assertNotEquals(tokenId, s.getCurrentTokenId());
        assertFalse(s.isRevoked());
        assertTrue(s.getExpiresAt().isAfter(Instant.now().plus(6, ChronoUnit.DAYS)));

        verify(repo).findBySessionId(sessionId);
        verify(repo).save(session);
    }

    @Test
    @DisplayName("Test revoke session")
    void testRevokeSession_whenSessionIdAndTokenIdProvided_returnNone() {
        // Arrange
        UUID sessionId = UUID.randomUUID();
        UUID tokenId = UUID.randomUUID();

        RefreshSession session = new RefreshSession();

        session.setSessionId(sessionId);
        session.setCurrentTokenId(tokenId);
        session.setRevoked(false);
        session.setExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS));

        when(repo.findBySessionId(sessionId)).thenReturn(Optional.of(session));

        when(repo.save(session)).thenReturn(session);

        // Act
        Instant before = Instant.now();

        service.revokeSession(sessionId, tokenId);

        Instant after = Instant.now();


        // Assert
        assertTrue(session.isRevoked());
        assertFalse(session.getRevokedAt().isBefore(before));
        assertFalse(session.getRevokedAt().isAfter(after));
        assertEquals(RevocationReason.LOGOUT, session.getRevocationReason());
    }
}
