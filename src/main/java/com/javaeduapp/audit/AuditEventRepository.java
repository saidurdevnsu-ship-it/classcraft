package com.javaeduapp.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {
    Page<AuditEvent> findByActorUsernameOrderByCreatedAtDesc(String username, Pageable pageable);
    Page<AuditEvent> findByTargetTypeAndTargetId(String targetType, Long targetId, Pageable pageable);
}
