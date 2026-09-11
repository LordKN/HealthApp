package com.HealthApp.service;

import com.HealthApp.exception.InvalidRefreshTokenException;
import com.HealthApp.model.RefreshSession;
import com.HealthApp.model.RevocationReason;
import com.HealthApp.model.Role;
import com.HealthApp.repo.RefreshSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshSessionService {

    @Autowired
    private RefreshSessionRepository repo;

    public RefreshSession createSession (Long userId, Role role) {
        RefreshSession session = new RefreshSession();

        session.setSessionId(UUID.randomUUID());
        session.setCurrentTokenId(UUID.randomUUID());

        session.setUserId(userId);
        session.setRole(role);
        session.setCreatedAt(Instant.now());
        session.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        session.setRevoked(false);
        return repo.save(session);
    }

    public RefreshSession refreshSession (UUID sessionId, UUID tokenId) {
        RefreshSession session = repo.findBySessionId(sessionId)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (session.isRevoked() || session.getExpiresAt().isBefore(Instant.now()) || !session.getCurrentTokenId().equals(tokenId)) {
            throw new InvalidRefreshTokenException();
        }

        else {
            UUID newTokenId = UUID.randomUUID();
            Instant expiresAt = Instant.now().plus(7, ChronoUnit.DAYS);
            session.setCurrentTokenId(newTokenId);
            session.setExpiresAt(expiresAt);
        }

        return repo.save(session);
    }

    public void revokeSession(UUID sessionId, UUID tokenId) {
        RefreshSession session = repo.findBySessionId(sessionId)
                .orElseThrow(InvalidRefreshTokenException::new);
        if (!session.getCurrentTokenId().equals(tokenId)) {
            throw new InvalidRefreshTokenException();
        }
        session.setRevoked(true);
        session.setRevokedAt(Instant.now());
        session.setRevocationReason(RevocationReason.LOGOUT);
        repo.save(session);
    }
}
