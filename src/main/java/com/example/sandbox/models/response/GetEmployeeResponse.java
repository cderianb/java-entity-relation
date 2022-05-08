package com.example.sandbox.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeResponse {
    public Long id;
    public String name;
    public String address;
    public Integer salary;
    public String department;
    public String organization;
}
