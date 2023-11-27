package com.recordsystem.faculty.repository;

import com.recordsystem.faculty.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineRepository extends CrudRepository<Discipline, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdIsNot(String name, Long id);
}