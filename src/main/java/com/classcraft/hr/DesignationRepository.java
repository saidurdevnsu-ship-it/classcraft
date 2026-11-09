package com.classcraft.hr;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignationRepository extends JpaRepository<Designation, Long> {
    List<Designation> findByDepartmentId(Long departmentId);
}
