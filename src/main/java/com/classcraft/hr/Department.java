package com.classcraft.hr;

import com.classcraft.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department extends BaseEntity {

    @NotBlank
    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(length = 500)
    private String description;
}
