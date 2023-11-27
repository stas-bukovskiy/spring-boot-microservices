package com.recordsystem.faculty.repository;

import com.recordsystem.faculty.model.Faculty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Long> {

    List<Faculty> findAll();

    boolean existsByName(String name);
}
