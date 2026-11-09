package com.classcraft.questionbank;

import com.classcraft.common.ApiResponse;
import com.classcraft.common.EntityNotFoundException;
import com.classcraft.questionbank.dto.GenerateQuestionsRequest;
import com.classcraft.questionbank.dto.QuestionRequest;
import com.classcraft.user.User;
import com.classcraft.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Question>>> list(
            @RequestParam(required = false) Long subjectId, Pageable pageable) {
        Page<Question> page = subjectId != null
                ? questionRepository.findBySubjectId(subjectId, pageable)
                : questionRepository.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.ok(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> get(@PathVariable Long id) {
        Question q = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question", id));
        return ResponseEntity.ok(ApiResponse.ok(q));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Question>> create(
            @Valid @RequestBody QuestionRequest req,
            @AuthenticationPrincipal UserDetails principal) {
        Subject subject = subjectRepository.findById(req.subjectId())
                .orElseThrow(() -> new EntityNotFoundException("Subject", req.subjectId()));
        User creator = userRepository.findByUsername(principal.getUsername()).orElseThrow();

        Question question = Question.builder()
                .subject(subject)
                .questionText(req.questionText())
                .questionType(req.questionType() != null
                        ? Question.QuestionType.valueOf(req.questionType()) : Question.QuestionType.MCQ)
                .optionA(req.optionA()).optionB(req.optionB())
                .optionC(req.optionC()).optionD(req.optionD())
                .correctOption(req.correctOption())
                .explanation(req.explanation())
                .difficulty(req.difficulty())
                .createdBy(creator)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(questionRepository.save(question)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> update(
            @PathVariable Long id, @Valid @RequestBody QuestionRequest req) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question", id));
        question.setQuestionText(req.questionText());
        question.setOptionA(req.optionA());
        question.setOptionB(req.optionB());
        question.setOptionC(req.optionC());
        question.setOptionD(req.optionD());
        question.setCorrectOption(req.correctOption());
        question.setExplanation(req.explanation());
        question.setDifficulty(req.difficulty());
        return ResponseEntity.ok(ApiResponse.ok(questionRepository.save(question)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Question", id));
        questionRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<Void>> generate(@Valid @RequestBody GenerateQuestionsRequest req) {
        // AI integration is deferred — returns 501 Not Implemented
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(ApiResponse.error("AI question generation is not yet implemented"));
    }
}
