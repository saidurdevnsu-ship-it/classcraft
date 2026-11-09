package com.classcraft.notification;

import com.classcraft.common.BaseEntity;
import com.classcraft.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(length = 50)
    private String notificationType;

    @Column(nullable = false)
    @Builder.Default
    private boolean isRead = false;

    @Column(length = 100)
    private String targetType;

    private Long targetId;
}
