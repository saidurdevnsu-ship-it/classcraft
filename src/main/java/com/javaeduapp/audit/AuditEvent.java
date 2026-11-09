package com.javaeduapp.audit;

import com.javaeduapp.common.BaseEntity;
import com.javaeduapp.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "audit_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditEvent extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private User actor;

    @Column(nullable = false, length = 100)
    private String action;

    @Column(length = 100)
    private String targetType;

    private Long targetId;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(length = 45)
    private String ipAddress;
}
