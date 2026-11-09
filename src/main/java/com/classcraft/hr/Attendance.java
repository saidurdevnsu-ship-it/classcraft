package com.classcraft.hr;

import com.classcraft.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employee_id", "attendance_date"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    private LocalTime punchIn;
    private LocalTime punchOut;

    @Column(length = 20)
    @Builder.Default
    private String status = "PRESENT"; // PRESENT, ABSENT, LATE, HALF_DAY

    @Column(columnDefinition = "TEXT")
    private String note;
}
