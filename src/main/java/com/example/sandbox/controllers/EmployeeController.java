package com.example.sandbox.controllers;

import com.example.sandbox.entities.Department;
import com.example.sandbox.entities.Employee;
import com.example.sandbox.entities.EmployeeDetail;
import com.example.sandbox.entities.Organization;
import com.example.sandbox.models.request.CreateEmployeeRequest;
import com.example.sandbox.models.response.GetEmployeeResponse;
import com.example.sandbox.repositories.EmployeeRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = EmployeeController.BASE_PATH)
public class EmployeeController {
    public static final String BASE_PATH = "/v1/employees";

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<GetEmployeeResponse>> getEmployees(){
        List<Employee> employeeList = employeeRepository.findAll();

        return toGetEmployeeResponse(employeeList);
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GetEmployeeResponse> getEmployeeById(@PathVariable Long id){
        Optional<Employee> result = employeeRepository.findById(id);
        if(result.isEmpty()){
            throw HttpClientErrorException.create(HttpStatus.OK, "Data Not Found", HttpHeaders.EMPTY, null, null);
        }
        Employee employee = result.get();
        GetEmployeeResponse response = GetEmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .department(employee.getDepartment().getName())
                .organization(employee.getOrganization().getName())
                .address(employee.getEmployeeDetails().getAddress())
                .salary(employee.getEmployeeDetails().getSalary())
                .build();

        return Mono.just(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Employee> createEmployee(@RequestBody CreateEmployeeRequest request){
        EmployeeDetail employeeDetails = EmployeeDetail.builder()
                .organization(Organization.builder().id(request.getOrganizationId()).build())
                .salary(request.getSalary())
                .address(request.getAddress())
                .build();
        Employee employee = Employee.builder()
                .name(request.getName())
                .department(Department.builder().id(request.getDepartmentId()).build())
                .organization(Organization.builder().id(request.getOrganizationId()).build())
                .build();
        employee.setEmployeeDetails(employeeDetails);
        employeeRepository.save(employee);

        return Mono.just(employee);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Employee> updateEmployee(@PathVariable Long id, @RequestBody CreateEmployeeRequest request){
        Optional<Employee> result = employeeRepository.findById(id);
        if(result.isEmpty()){
            throw HttpClientErrorException.create(HttpStatus.OK, "Data Not Found", HttpHeaders.EMPTY, null, null);
        }
        Employee employee = result.get();
        EmployeeDetail detail = employee.getEmployeeDetails();
        detail.setAddress(request.getAddress());
        detail.setSalary(request.getSalary());

        employee.setName(request.getName());
        employee.setDepartment(Department.builder().id(request.getDepartmentId()).build());
        employee.setOrganization(Organization.builder().id(request.getOrganizationId()).build());
        employee.setEmployeeDetails(detail);

        employeeRepository.save(employee);

        return Mono.just(employee);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Long> deleteEmployee(@PathVariable Long id){
        employeeRepository.deleteById(id);
        return Mono.just(id);
    }

    @GetMapping(path = "/salary", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<GetEmployeeResponse>> getEmployeeWithSalaryRange(@RequestParam Integer min,
                                                                      @RequestParam Integer max){
        List<Employee> employeeList = employeeRepository.findByEmployeeDetails_SalaryBetween(min, max);

        return toGetEmployeeResponse(employeeList);
    }

    private Mono<List<GetEmployeeResponse>> toGetEmployeeResponse(List<Employee> employeeList) {
        List<GetEmployeeResponse> response = employeeList.stream()
                .map(employee ->
                        GetEmployeeResponse.builder()
                                .id(employee.getId())
                                .name(employee.getName())
                                .department(employee.getDepartment().getName())
                                .organization(employee.getOrganization().getName())
                                .address(employee.getEmployeeDetails().getAddress())
                                .salary(employee.getEmployeeDetails().getSalary())
                                .build())
                .collect(Collectors.toList());

        return Mono.just(response);
    }
}
