package com.example.sandbox.controllers;

import com.example.sandbox.entities.Department;
import com.example.sandbox.entities.Employee;
import com.example.sandbox.entities.Organization;
import com.example.sandbox.entities.Task;
import com.example.sandbox.models.request.CreateEmployeeTaskRequest;
import com.example.sandbox.models.request.CreateTaskRequest;
import com.example.sandbox.repositories.EmployeeRepository;
import com.example.sandbox.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = EmployeeTaskController.BASE_PATH)
public class EmployeeTaskController {
    public static final String BASE_PATH = "/v1/employee-tasks";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Employee> assignEmployeeTask(@RequestBody CreateEmployeeTaskRequest request){
        Optional<Employee> result = employeeRepository.findById(request.getEmployeeId());
        if(result.isEmpty()){
            throw HttpClientErrorException.create(HttpStatus.OK, "Data Not Found", HttpHeaders.EMPTY, null, null);
        }
        Employee employee = result.get();
        Organization organization = Organization.builder().id(request.getOrganizationId()).build();
        Set<Task> tasks = request.getTaskId().stream()
                .map(taskId -> Task.builder()
                        .organization(organization)
                        .id(taskId).build())
                .collect(Collectors.toSet());

        employee.setTasks(tasks);
        employeeRepository.save(employee);

        return Mono.just(employee);
    }

}
