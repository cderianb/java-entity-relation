package com.example.sandbox.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {
    private String name;
    private String description;
    private Long organizationId;
    private Long departmentId;
}
