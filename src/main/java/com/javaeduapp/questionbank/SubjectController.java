package com.javaeduapp.questionbank;

import com.javaeduapp.common.ApiResponse;
import com.javaeduapp.common.EntityNotFoundException;
import com.javaeduapp.questionbank.dto.SubjectRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final GradeRepository gradeRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Subject>>> list(@RequestParam(required = false) Long gradeId) {
        List<Subject> subjects = gradeId != null
                ? subjectRepository.findByGradeId(gradeId)
                : subjectRepository.findAll();
        return ResponseEntity.ok(ApiResponse.ok(subjects));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Subject>> get(@PathVariable Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject", id));
        return ResponseEntity.ok(ApiResponse.ok(subject));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Subject>> create(@Valid @RequestBody SubjectRequest req) {
        Subject.SubjectBuilder builder = Subject.builder()
                .name(req.name()).code(req.code()).description(req.description());
        if (req.gradeId() != null) {
            Grade grade = gradeRepository.findById(req.gradeId())
                    .orElseThrow(() -> new EntityNotFoundException("Grade", req.gradeId()));
            builder.grade(grade);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(subjectRepository.save(builder.build())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Subject>> update(@PathVariable Long id,
                                                        @Valid @RequestBody SubjectRequest req) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject", id));
        subject.setName(req.name());
        subject.setCode(req.code());
        subject.setDescription(req.description());
        if (req.gradeId() != null) {
            Grade grade = gradeRepository.findById(req.gradeId())
                    .orElseThrow(() -> new EntityNotFoundException("Grade", req.gradeId()));
            subject.setGrade(grade);
        }
        return ResponseEntity.ok(ApiResponse.ok(subjectRepository.save(subject)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subject", id));
        subjectRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
