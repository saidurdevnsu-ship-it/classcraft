package com.javaeduapp.audit;

import com.javaeduapp.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditEventRepository repository;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<AuditEvent>>> list(
            @RequestParam(required = false) String targetType,
            @RequestParam(required = false) Long targetId,
            Pageable pageable) {
        Page<AuditEvent> page;
        if (targetType != null && targetId != null) {
            page = repository.findByTargetTypeAndTargetId(targetType, targetId, pageable);
        } else {
            page = repository.findAll(pageable);
        }
        return ResponseEntity.ok(ApiResponse.ok(page));
    }
}
