package com.example.sandbox.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrganizationDepartmentsResponse {
    public Long id;
    public String code;
    public String name;
    public List<String> departments;
}
