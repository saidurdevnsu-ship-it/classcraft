package com.classcraft.hr;

import com.classcraft.common.ApiResponse;
import com.classcraft.common.EntityNotFoundException;
import com.classcraft.hr.dto.DesignationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designations")
@RequiredArgsConstructor
public class DesignationController {

    private final DesignationRepository repository;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Designation>>> list(
            @RequestParam(required = false) Long departmentId) {
        List<Designation> list = departmentId != null
                ? repository.findByDepartmentId(departmentId)
                : repository.findAll();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Designation>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Designation", id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Designation>> create(@Valid @RequestBody DesignationRequest req) {
        Designation.DesignationBuilder builder = Designation.builder().title(req.title()).description(req.description());
        if (req.departmentId() != null) {
            builder.department(departmentRepository.findById(req.departmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department", req.departmentId())));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(repository.save(builder.build())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Designation>> update(
            @PathVariable Long id, @Valid @RequestBody DesignationRequest req) {
        Designation des = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Designation", id));
        des.setTitle(req.title());
        des.setDescription(req.description());
        return ResponseEntity.ok(ApiResponse.ok(repository.save(des)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Designation", id));
        repository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
