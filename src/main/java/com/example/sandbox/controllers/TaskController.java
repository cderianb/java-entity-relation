package com.example.sandbox.controllers;

import com.example.sandbox.entities.Department;
import com.example.sandbox.entities.Employee;
import com.example.sandbox.entities.EmployeeDetail;
import com.example.sandbox.entities.Organization;
import com.example.sandbox.entities.Task;
import com.example.sandbox.models.request.CreateEmployeeRequest;
import com.example.sandbox.models.request.CreateTaskRequest;
import com.example.sandbox.models.response.GetDepartmentResponse;
import com.example.sandbox.models.response.GetEmployeeResponse;
import com.example.sandbox.models.response.GetTaskResponse;
import com.example.sandbox.repositories.TaskRepository;
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
@RequestMapping(value = TaskController.BASE_PATH)
public class TaskController {
    public static final String BASE_PATH = "/v1/tasks";

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<GetTaskResponse>> getTasks(){
        List<Task> taskList = taskRepository.findAll();

        return toGetTaskResponse(taskList);
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GetTaskResponse> getTaskById(@PathVariable Long id){
        Optional<Task> result = taskRepository.findById(id);
        if(result.isEmpty()){
            throw HttpClientErrorException.create(HttpStatus.OK, "Data Not Found", HttpHeaders.EMPTY, null, null);
        }
        Task task = result.get();
        GetTaskResponse response = GetTaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .department(task.getDepartment().getName())
                .organization(task.getOrganization().getName())
                .description(task.getDescription())
                .build();

        return Mono.just(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Task> createTask(@RequestBody CreateTaskRequest request){
        Task task = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .department(Department.builder().id(request.getDepartmentId()).build())
                .organization(Organization.builder().id(request.getOrganizationId()).build())
                .build();
        taskRepository.save(task);

        return Mono.just(task);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Task> updateTask(@PathVariable Long id, @RequestBody CreateTaskRequest request){
        Optional<Task> result = taskRepository.findById(id);
        if(result.isEmpty()){
            throw HttpClientErrorException.create(HttpStatus.OK, "Data Not Found", HttpHeaders.EMPTY, null, null);
        }
        Task task = result.get();

        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setDepartment(Department.builder().id(request.getDepartmentId()).build());
        task.setOrganization(Organization.builder().id(request.getOrganizationId()).build());

        taskRepository.save(task);

        return Mono.just(task);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Long> deleteTask(@PathVariable Long id){
        taskRepository.deleteById(id);
        return Mono.just(id);
    }

    private Mono<List<GetTaskResponse>> toGetTaskResponse(List<Task> taskList) {
        List<GetTaskResponse> response = taskList.stream()
                .map(task ->
                        GetTaskResponse.builder()
                                .id(task.getId())
                                .name(task.getName())
                                .description(task.getDescription())
                                .department(task.getDepartment().getName())
                                .organization(task.getOrganization().getName())
                                .build())
                .collect(Collectors.toList());

        return Mono.just(response);
    }
}
