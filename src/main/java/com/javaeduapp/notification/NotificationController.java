package com.javaeduapp.notification;

import com.javaeduapp.common.ApiResponse;
import com.javaeduapp.common.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository repository;
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Notification>>> list(
            @AuthenticationPrincipal UserDetails principal, Pageable pageable) {
        Page<Notification> page = repository.findByRecipientUsernameOrderByCreatedAtDesc(
                principal.getUsername(), pageable);
        return ResponseEntity.ok(ApiResponse.ok(page));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Notification>> markRead(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails principal) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification", id));
        if (!notification.getRecipient().getUsername().equals(principal.getUsername())) {
            return ResponseEntity.status(403).body(ApiResponse.error("Access denied"));
        }
        notification.setRead(true);
        return ResponseEntity.ok(ApiResponse.ok(repository.save(notification)));
    }
}
