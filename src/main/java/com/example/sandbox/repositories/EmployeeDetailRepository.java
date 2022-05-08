package com.example.sandbox.repositories;

import com.example.sandbox.entities.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Long> {
}
