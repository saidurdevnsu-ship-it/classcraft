package com.javaeduapp.hr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    @Query("SELECT h FROM Holiday h WHERE YEAR(h.holidayDate) = :year")
    List<Holiday> findByHolidayDateYear(@Param("year") int year);
}
