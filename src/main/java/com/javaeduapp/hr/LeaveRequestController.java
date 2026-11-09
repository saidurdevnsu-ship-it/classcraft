package com.javaeduapp.hr;

import com.javaeduapp.common.ApiResponse;
import com.javaeduapp.common.EntityNotFoundException;
import com.javaeduapp.hr.dto.LeaveRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestRepository repository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<LeaveRequest>>> list(
            @RequestParam(required = false) String status, Pageable pageable) {
        Page<LeaveRequest> page = status != null
                ? repository.findByStatus(status, pageable)
                : repository.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.ok(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveRequest>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("LeaveRequest", id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LeaveRequest>> create(
            @Valid @RequestBody LeaveRequestDto req,
            @AuthenticationPrincipal UserDetails principal) {
        Employee employee = employeeRepository.findByUserUsername(principal.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Employee record not found for user: " + principal.getUsername()));
        LeaveType leaveType = leaveTypeRepository.findById(req.leaveTypeId())
                .orElseThrow(() -> new EntityNotFoundException("LeaveType", req.leaveTypeId()));

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employee(employee).leaveType(leaveType)
                .startDate(req.startDate()).endDate(req.endDate())
                .reason(req.reason()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(repository.save(leaveRequest)));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<LeaveRequest>> approve(@PathVariable Long id,
                                                              @RequestParam String action,
                                                              @RequestParam(required = false) String note) {
        LeaveRequest lr = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeaveRequest", id));
        lr.setStatus("APPROVED".equalsIgnoreCase(action) ? "APPROVED" : "REJECTED");
        lr.setApproverNote(note);
        return ResponseEntity.ok(ApiResponse.ok(repository.save(lr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("LeaveRequest", id));
        repository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
