package com.classcraft.hr;

import com.classcraft.common.ApiResponse;
import com.classcraft.common.EntityNotFoundException;
import com.classcraft.hr.dto.EmployeeRequest;
import com.classcraft.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository repository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(repository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee", id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> create(@Valid @RequestBody EmployeeRequest req) {
        var user = userRepository.findById(req.userId())
                .orElseThrow(() -> new EntityNotFoundException("User", req.userId()));
        Employee.EmployeeBuilder builder = Employee.builder()
                .user(user).employeeId(req.employeeId())
                .joiningDate(req.joiningDate()).employmentType(req.employmentType()).phone(req.phone());
        if (req.departmentId() != null) {
            builder.department(departmentRepository.findById(req.departmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department", req.departmentId())));
        }
        if (req.designationId() != null) {
            builder.designation(designationRepository.findById(req.designationId())
                    .orElseThrow(() -> new EntityNotFoundException("Designation", req.designationId())));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(repository.save(builder.build())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> update(
            @PathVariable Long id, @Valid @RequestBody EmployeeRequest req) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee", id));
        emp.setEmployeeId(req.employeeId());
        emp.setJoiningDate(req.joiningDate());
        emp.setEmploymentType(req.employmentType());
        emp.setPhone(req.phone());
        if (req.departmentId() != null) {
            emp.setDepartment(departmentRepository.findById(req.departmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department", req.departmentId())));
        }
        if (req.designationId() != null) {
            emp.setDesignation(designationRepository.findById(req.designationId())
                    .orElseThrow(() -> new EntityNotFoundException("Designation", req.designationId())));
        }
        return ResponseEntity.ok(ApiResponse.ok(repository.save(emp)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee", id));
        repository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
