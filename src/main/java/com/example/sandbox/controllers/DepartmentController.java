package com.example.sandbox.controllers;

import com.example.sandbox.entities.Department;
import com.example.sandbox.entities.Organization;
import com.example.sandbox.models.request.CreateDepartmentRequest;
import com.example.sandbox.models.response.GetDepartmentEmployeesResponse;
import com.example.sandbox.models.response.GetDepartmentResponse;
import com.example.sandbox.repositories.DepartmentRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = DepartmentController.BASE_PATH)
public class DepartmentController {
    public static final String BASE_PATH = "/v1/departments";

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<GetDepartmentResponse>> getDepartment(){
        List<Department> departmentList = departmentRepository.findAll();

        List<GetDepartmentResponse> response = departmentList.stream()
                .map(department ->
                        GetDepartmentResponse.builder()
                                .id(department.getId())
                                .name(department.getName())
                                .organizationId(department.getOrganization().getId())
                                .organizationCode(department.getOrganization().getCode())
                                .organizationName(department.getOrganization().getName())
                                .build())
                .collect(Collectors.toList());

        return Mono.just(response);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GetDepartmentResponse> getDepartmentById(@PathVariable Long id){
        Optional<Department> result = departmentRepository.findById(id);
        return toGetDepartmentResponse(result);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Department> createDepartment(@RequestBody CreateDepartmentRequest request){
        Department department = Department.builder()
                .name(request.getName())
                .organization(Organization.builder().id(request.getOrganizationId()).build())
                .build();
        departmentRepository.save(department);

        return Mono.just(department);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Department> updateDepartment(@PathVariable Long id, @RequestBody CreateDepartmentRequest request){
        Optional<Department> result = departmentRepository.findById(id);
        if(result.isEmpty()){
            throw HttpClientErrorException.create(HttpStatus.OK, "Data Not Found", HttpHeaders.EMPTY, null, null);
        }
        Department department = result.get();
        department.setName(request.getName());
        departmentRepository.save(department);

        return Mono.just(department);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Long> deleteDepartment(@PathVariable Long id){
        departmentRepository.deleteById(id);
        return Mono.just(id);
    }

    @GetMapping(path = "/{id}/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GetDepartmentEmployeesResponse> getDepartmentEmployees(@PathVariable Long id){
        Optional<Department> result = departmentRepository.findById(id);
        if (result.isEmpty()){
            throw new HttpClientErrorException(HttpStatus.OK, "Data Not Found");
        }
        Department department = result.get();
        Set<GetDepartmentEmployeesResponse.EmployeeInformation> employees =
                department.getEmployees().stream()
                        .map(employee -> GetDepartmentEmployeesResponse.EmployeeInformation.builder()
                                .id(employee.getId())
                                .name(employee.getName())
                                .salary(employee.getEmployeeDetails().getSalary())
                                .address(employee.getEmployeeDetails().getAddress())
                                .build())
                        .collect(Collectors.toSet());
        GetDepartmentEmployeesResponse response = GetDepartmentEmployeesResponse.builder()
                .id(department.getId())
                .organizationId(department.getOrganization().getId())
                .departmentName(department.getName())
                .employees(employees)
                .build();

        return Mono.just(response);
    }

    @GetMapping(path = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GetDepartmentResponse> getDepartmentById(@PathVariable String name){
        Optional<Department> result = departmentRepository.findByName(name);
        return toGetDepartmentResponse(result);
    }

    private Mono<GetDepartmentResponse> toGetDepartmentResponse(Optional<Department> result) {
        if(result.isEmpty()){
            throw HttpClientErrorException.create(HttpStatus.OK, "Data Not Found", HttpHeaders.EMPTY, null, null);
        }
        Department department = result.get();
        GetDepartmentResponse response = GetDepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .organizationId(department.getOrganization().getId())
                .organizationCode(department.getOrganization().getCode())
                .organizationName(department.getOrganization().getName())
                .build();

        return Mono.just(response);
    }
}
