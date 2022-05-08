package com.example.sandbox.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationsResponse {
    public Long id;
    public String code;
    public String name;
}
