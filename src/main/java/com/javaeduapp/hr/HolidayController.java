package com.javaeduapp.hr;

import com.javaeduapp.common.ApiResponse;
import com.javaeduapp.common.EntityNotFoundException;
import com.javaeduapp.hr.dto.HolidayRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayRepository repository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Holiday>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(repository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Holiday>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Holiday", id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Holiday>> create(@Valid @RequestBody HolidayRequest req) {
        Holiday holiday = Holiday.builder()
                .name(req.name()).holidayDate(req.holidayDate())
                .holidayType(req.holidayType()).description(req.description()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(repository.save(holiday)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Holiday>> update(
            @PathVariable Long id, @Valid @RequestBody HolidayRequest req) {
        Holiday holiday = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Holiday", id));
        holiday.setName(req.name());
        holiday.setHolidayDate(req.holidayDate());
        holiday.setHolidayType(req.holidayType());
        holiday.setDescription(req.description());
        return ResponseEntity.ok(ApiResponse.ok(repository.save(holiday)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Holiday", id));
        repository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }
}
