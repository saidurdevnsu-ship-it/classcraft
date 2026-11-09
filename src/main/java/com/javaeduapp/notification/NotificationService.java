package com.javaeduapp.notification;

import com.javaeduapp.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    /**
     * Creates and persists an in-app notification for the given recipient.
     */
    @Transactional
    public Notification notify(User recipient, String title, String body,
                               String notificationType, String targetType, Long targetId) {
        Notification notification = Notification.builder()
                .recipient(recipient)
                .title(title)
                .body(body)
                .notificationType(notificationType)
                .targetType(targetType)
                .targetId(targetId)
                .build();
        return repository.save(notification);
    }

    @Transactional
    public Notification markRead(Long notificationId) {
        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new com.javaeduapp.common.EntityNotFoundException("Notification", notificationId));
        notification.setRead(true);
        return repository.save(notification);
    }
}
