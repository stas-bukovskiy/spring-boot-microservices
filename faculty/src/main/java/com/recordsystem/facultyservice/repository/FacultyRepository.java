package com.recordsystem.facultyservice.repository;

import com.recordsystem.facultyservice.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Long> {

    List<Faculty> findAll();
}
