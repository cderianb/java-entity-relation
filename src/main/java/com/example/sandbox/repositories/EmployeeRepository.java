package com.example.sandbox.repositories;

import com.example.sandbox.entities.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"organization", "department", "employeeDetails"})
    List<Employee> findAll();

    List<Employee> findByEmployeeDetails_SalaryBetween(Integer min, Integer max);
}
