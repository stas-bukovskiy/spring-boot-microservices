package com.recordsystem.facultyservice.repository;

import com.recordsystem.facultyservice.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
}
