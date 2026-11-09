package com.classcraft.audit;

import com.classcraft.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditEventRepository repository;

    /**
     * Records an audit event. Runs asynchronously so it never blocks the main request.
     *
     * @param actor      the user performing the action (may be null for system events)
     * @param action     short verb describing the action, e.g. "LOGIN", "CREATE_QUESTION"
     * @param targetType entity class name, e.g. "Question"
     * @param targetId   primary key of the affected entity
     * @param details    free-form JSON or text with extra context
     * @param ip         client IP address
     */
    @Async
    @Transactional
    public void record(User actor, String action, String targetType, Long targetId,
                       String details, String ip) {
        AuditEvent event = AuditEvent.builder()
                .actor(actor)
                .action(action)
                .targetType(targetType)
                .targetId(targetId)
                .details(details)
                .ipAddress(ip)
                .build();
        repository.save(event);
    }
}
