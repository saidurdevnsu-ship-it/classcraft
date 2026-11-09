package com.javaeduapp.hr;

import com.javaeduapp.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "holidays")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Holiday extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private LocalDate holidayDate;

    @Column(length = 50)
    private String holidayType; // PUBLIC, OPTIONAL, SCHOOL

    @Column(columnDefinition = "TEXT")
    private String description;
}
