package com.example.sandbox.models.response;

import com.example.sandbox.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDepartmentEmployeesResponse {
    public Long id;
    public Long organizationId;
    public String departmentName;
    public Set<EmployeeInformation> employees;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeInformation{
        public Long id;
        public String name;
        public Integer salary;
        public String address;
    }
}
