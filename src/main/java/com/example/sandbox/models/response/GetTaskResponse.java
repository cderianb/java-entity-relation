package com.example.sandbox.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTaskResponse {
    public Long id;
    public String department;
    public String organization;
    public String name;
    public String description;
}
