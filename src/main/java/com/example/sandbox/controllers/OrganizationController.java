package com.example.sandbox.controllers;

import com.example.sandbox.entities.Department;
import com.example.sandbox.entities.Organization;
import com.example.sandbox.models.request.CreateOrganizationRequest;
import com.example.sandbox.models.response.GetOrganizationDepartmentsResponse;
import com.example.sandbox.models.response.GetOrganizationsResponse;
import com.example.sandbox.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = OrganizationController.BASE_PATH)
public class OrganizationController {
    public static final String BASE_PATH = "/v1/organizations";

    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<GetOrganizationsResponse>> getOrganizations(){
        List<Organization> organizationList = organizationRepository.findAll();

        List<GetOrganizationsResponse> response = organizationList.stream()
                .map(organization ->
                        GetOrganizationsResponse.builder()
                            .id(organization.getId())
                            .code(organization.getCode())
                            .name(organization.getName())
                            .build())
                .collect(Collectors.toList());

        return Mono.just(response);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GetOrganizationsResponse> getOrganizationById(@PathVariable Long id){
        Optional<Organization> result = organizationRepository.findById(id);
        if(result.isEmpty()){
            throw HttpClientErrorException.create(HttpStatus.OK, "Data Not Found", HttpHeaders.EMPTY, null, null);
        }
        Organization organization = result.get();

        GetOrganizationsResponse response = GetOrganizationsResponse.builder()
                                .id(organization.getId())
                                .code(organization.getCode())
                                .name(organization.getName())
                                .build();

        return Mono.just(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Organization> createOrganization(@RequestBody CreateOrganizationRequest request){
        Organization organization = Organization.builder()
                .code(request.getCode())
                .name(request.getName())
                .build();
        organizationRepository.save(organization);

        return Mono.just(organization);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Organization> updateOrganization(@PathVariable Long id, @RequestBody CreateOrganizationRequest request){
        Optional<Organization> result = organizationRepository.findById(id);
        if(result.isEmpty()){
            throw HttpClientErrorException.create(HttpStatus.OK, "Data Not Found", HttpHeaders.EMPTY, null, null);
        }
        Organization organization = result.get();

        organization.setName(request.getName());
        organization.setCode(request.getCode());

        organizationRepository.save(organization);

        return Mono.just(organization);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Long> deleteOrganization(@PathVariable Long id){
        organizationRepository.deleteById(id); // will select data first by jpa
        return Mono.just(id);
    }

    @GetMapping(path = "/{id}/departments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GetOrganizationDepartmentsResponse> getOrganizationDepartments(@PathVariable Long id){
        Optional<Organization> result = organizationRepository.findById(id);
        if (result.isEmpty()){
            throw new HttpClientErrorException(HttpStatus.OK, "Data Not Found");
        }
        Organization organization = result.get();
        GetOrganizationDepartmentsResponse response = GetOrganizationDepartmentsResponse.builder()
                .id(organization.getId())
                .code(organization.getCode())
                .name(organization.getName())
                .departments(organization.getDepartments().stream().map(Department::getName).collect(Collectors.toList()))
                .build();

        return Mono.just(response);
    }
}
