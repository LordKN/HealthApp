package com.HealthApp.repo;

import com.HealthApp.model.RefreshSession;
import com.HealthApp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshSessionRepository extends JpaRepository<RefreshSession, Long> {
    Optional<RefreshSession> findBySessionId(UUID sessionId);
    List<RefreshSession> findByUserIdAndRole(Long userId, Role role);
}
