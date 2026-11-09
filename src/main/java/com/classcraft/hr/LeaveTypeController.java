package com.classcraft.hr;

import com.classcraft.common.ApiResponse;
import com.classcraft.common.EntityNotFoundException;
import com.classcraft.hr.dto.LeaveTypeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-types")
@RequiredArgsConstructor
public class LeaveTypeController {

    private final LeaveTypeRepository repository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaveType>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(repository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveType>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("LeaveType", id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LeaveType>> create(@Valid @RequestBody LeaveTypeRequest req) {
        LeaveType lt = LeaveType.builder()
                .name(req.name()).maxDaysPerYear(req.maxDaysPerYear()).description(req.description()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(repository.save(lt)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveType>> update(
            @PathVariable Long id, @Valid @RequestBody LeaveTypeRequest req) {
        LeaveType lt = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeaveType", id));
        lt.setName(req.name());
        lt.setMaxDaysPerYear(req.maxDaysPerYear());
        lt.setDescription(req.description());
        return ResponseEntity.ok(ApiResponse.ok(repository.save(lt)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("LeaveType", id));
        repository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
