package com.classcraft.hr;

import com.classcraft.common.ApiResponse;
import com.classcraft.common.EntityNotFoundException;
import com.classcraft.hr.dto.DepartmentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentRepository repository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Department>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(repository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Department", id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Department>> create(@Valid @RequestBody DepartmentRequest req) {
        Department dept = Department.builder().name(req.name()).description(req.description()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(repository.save(dept)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> update(
            @PathVariable Long id, @Valid @RequestBody DepartmentRequest req) {
        Department dept = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department", id));
        dept.setName(req.name());
        dept.setDescription(req.description());
        return ResponseEntity.ok(ApiResponse.ok(repository.save(dept)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Department", id));
        repository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
