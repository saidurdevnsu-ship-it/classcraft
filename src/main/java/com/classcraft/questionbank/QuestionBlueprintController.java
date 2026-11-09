package com.classcraft.questionbank;

import com.classcraft.common.ApiResponse;
import com.classcraft.common.EntityNotFoundException;
import com.classcraft.questionbank.dto.BlueprintRequest;
import com.classcraft.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blueprints")
@RequiredArgsConstructor
public class QuestionBlueprintController {

    private final QuestionBlueprintRepository blueprintRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionBlueprint>>> list(
            @RequestParam(required = false) Long subjectId) {
        List<QuestionBlueprint> list = subjectId != null
                ? blueprintRepository.findBySubjectId(subjectId)
                : blueprintRepository.findAll();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionBlueprint>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(
                blueprintRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Blueprint", id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionBlueprint>> create(
            @Valid @RequestBody BlueprintRequest req,
            @AuthenticationPrincipal UserDetails principal) {
        Subject subject = subjectRepository.findById(req.subjectId())
                .orElseThrow(() -> new EntityNotFoundException("Subject", req.subjectId()));
        var creator = userRepository.findByUsername(principal.getUsername()).orElseThrow();

        QuestionBlueprint blueprint = QuestionBlueprint.builder()
                .title(req.title()).subject(subject).createdBy(creator)
                .academicYear(req.academicYear()).build();

        if (req.items() != null) {
            req.items().forEach(itemReq -> {
                BlueprintItem item = BlueprintItem.builder()
                        .blueprint(blueprint)
                        .topic(itemReq.topic())
                        .questionType(itemReq.questionType())
                        .questionCount(itemReq.questionCount())
                        .difficulty(itemReq.difficulty())
                        .marks(itemReq.marks())
                        .build();
                blueprint.getItems().add(item);
            });
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(blueprintRepository.save(blueprint)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionBlueprint>> update(
            @PathVariable Long id, @Valid @RequestBody BlueprintRequest req) {
        QuestionBlueprint blueprint = blueprintRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Blueprint", id));
        blueprint.setTitle(req.title());
        blueprint.setAcademicYear(req.academicYear());
        return ResponseEntity.ok(ApiResponse.ok(blueprintRepository.save(blueprint)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        blueprintRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Blueprint", id));
        blueprintRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
