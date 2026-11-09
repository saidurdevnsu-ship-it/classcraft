package com.javaeduapp.questionbank;

import com.javaeduapp.common.ApiResponse;
import com.javaeduapp.common.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge-sources")
@RequiredArgsConstructor
public class KnowledgeSourceController {

    private final KnowledgeSourceRepository repository;
    private final SubjectRepository subjectRepository;

    public record KnowledgeSourceRequest(
            @NotBlank String title, Long subjectId, String content, String sourceUrl) {}

    @GetMapping
    public ResponseEntity<ApiResponse<List<KnowledgeSource>>> list(
            @RequestParam(required = false) Long subjectId) {
        List<KnowledgeSource> list = subjectId != null
                ? repository.findBySubjectId(subjectId)
                : repository.findAll();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KnowledgeSource>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("KnowledgeSource", id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<KnowledgeSource>> create(@Valid @RequestBody KnowledgeSourceRequest req) {
        KnowledgeSource ks = KnowledgeSource.builder()
                .title(req.title()).content(req.content()).sourceUrl(req.sourceUrl()).build();
        if (req.subjectId() != null) {
            ks.setSubject(subjectRepository.findById(req.subjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Subject", req.subjectId())));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(repository.save(ks)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KnowledgeSource>> update(
            @PathVariable Long id, @Valid @RequestBody KnowledgeSourceRequest req) {
        KnowledgeSource ks = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("KnowledgeSource", id));
        ks.setTitle(req.title());
        ks.setContent(req.content());
        ks.setSourceUrl(req.sourceUrl());
        return ResponseEntity.ok(ApiResponse.ok(repository.save(ks)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("KnowledgeSource", id));
        repository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
