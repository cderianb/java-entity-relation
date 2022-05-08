package com.example.sandbox.repositories;

import com.example.sandbox.entities.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "organization")
    List<Department> findAll();

    Optional<Department> findByName(String name);
}
