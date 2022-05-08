package com.example.sandbox.models.response;

import com.example.sandbox.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDepartmentResponse {
    public Long id;
    public String name;
    public Long organizationId;
    public String organizationCode;
    public String organizationName;
}
