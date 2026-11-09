package com.classcraft.hr;

import com.classcraft.common.BaseEntity;
import com.classcraft.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(unique = true, length = 50)
    private String employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designation_id")
    private Designation designation;

    private LocalDate joiningDate;

    @Column(length = 20)
    private String employmentType; // FULL_TIME, PART_TIME, CONTRACT

    @Column(length = 20)
    @Builder.Default
    private String status = "ACTIVE";

    @Column(length = 20)
    private String phone;
}
