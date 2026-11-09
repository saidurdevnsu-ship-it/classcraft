package com.javaeduapp.questionbank;

import com.javaeduapp.common.ApiResponse;
import com.javaeduapp.common.EntityNotFoundException;
import com.javaeduapp.questionbank.dto.GradeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeRepository gradeRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Grade>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(gradeRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Grade>> get(@PathVariable Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade", id));
        return ResponseEntity.ok(ApiResponse.ok(grade));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Grade>> create(@Valid @RequestBody GradeRequest req) {
        Grade grade = Grade.builder().name(req.name()).description(req.description()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(gradeRepository.save(grade)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Grade>> update(@PathVariable Long id,
                                                      @Valid @RequestBody GradeRequest req) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade", id));
        grade.setName(req.name());
        grade.setDescription(req.description());
        return ResponseEntity.ok(ApiResponse.ok(gradeRepository.save(grade)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        gradeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Grade", id));
        gradeRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
