package com.example.sandbox.repositories;

import com.example.sandbox.entities.Department;
import com.example.sandbox.entities.Organization;
import com.example.sandbox.entities.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"organization", "department"} )
    List<Task> findAll();
}
