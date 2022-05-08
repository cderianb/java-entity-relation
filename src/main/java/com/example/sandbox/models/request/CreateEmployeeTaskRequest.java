package com.example.sandbox.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeTaskRequest {
    private Long organizationId;
    private Long employeeId;
    private Set<Long> taskId;
}
