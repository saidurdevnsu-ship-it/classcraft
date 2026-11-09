package com.classcraft.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByRecipientUsernameOrderByCreatedAtDesc(String username, Pageable pageable);
    long countByRecipientUsernameAndIsRead(String username, boolean isRead);
}
