package com.classcraft.questionbank;

import com.classcraft.common.ApiResponse;
import com.classcraft.common.EntityNotFoundException;
import com.classcraft.questionbank.dto.AcademicEventRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-events")
@RequiredArgsConstructor
public class AcademicEventController {

    private final AcademicEventRepository repository;
    private final SubjectRepository subjectRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AcademicEvent>>> list(
            @RequestParam(required = false) Long subjectId) {
        List<AcademicEvent> list = subjectId != null
                ? repository.findBySubjectId(subjectId)
                : repository.findAll();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicEvent>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("AcademicEvent", id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AcademicEvent>> create(@Valid @RequestBody AcademicEventRequest req) {
        AcademicEvent.AcademicEventBuilder builder = AcademicEvent.builder()
                .title(req.title()).description(req.description())
                .eventDate(req.eventDate()).eventType(req.eventType());
        if (req.subjectId() != null) {
            builder.subject(subjectRepository.findById(req.subjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Subject", req.subjectId())));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(repository.save(builder.build())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicEvent>> update(
            @PathVariable Long id, @Valid @RequestBody AcademicEventRequest req) {
        AcademicEvent event = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AcademicEvent", id));
        event.setTitle(req.title());
        event.setDescription(req.description());
        event.setEventDate(req.eventDate());
        event.setEventType(req.eventType());
        return ResponseEntity.ok(ApiResponse.ok(repository.save(event)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("AcademicEvent", id));
        repository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
