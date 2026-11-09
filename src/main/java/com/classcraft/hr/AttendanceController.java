package com.classcraft.hr;

import com.classcraft.common.ApiResponse;
import com.classcraft.common.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceRepository repository;
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Attendance>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(repository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Attendance>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attendance", id))));
    }

    /**
     * Punch in/out for the authenticated employee for today.
     * First punch creates the record (punchIn). Subsequent punches for the same day update punchOut.
     */
    @PostMapping("/punch")
    public ResponseEntity<ApiResponse<Attendance>> punch(@AuthenticationPrincipal UserDetails principal) {
        Employee employee = employeeRepository.findByUserUsername(principal.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Employee record not found for user: " + principal.getUsername()));

        LocalDate today = LocalDate.now();
        Attendance attendance = repository.findByEmployeeIdAndAttendanceDate(employee.getId(), today)
                .orElse(null);

        if (attendance == null) {
            attendance = Attendance.builder()
                    .employee(employee)
                    .attendanceDate(today)
                    .punchIn(LocalTime.now())
                    .status("PRESENT")
                    .build();
        } else {
            attendance.setPunchOut(LocalTime.now());
        }

        return ResponseEntity.ok(ApiResponse.ok(repository.save(attendance), "Punch recorded"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attendance", id));
        repository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
