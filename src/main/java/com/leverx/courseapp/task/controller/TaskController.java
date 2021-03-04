package com.leverx.courseapp.task.controller;

import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.model.Task;
import com.leverx.courseapp.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collection;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Api(value = "tasks")
@ApiResponses(
        value = {
                @ApiResponse(code = 400, message = "Bad Request"),
                @ApiResponse(code = 500, message = "Internal Server Error")
        })
@RequestMapping(value = "/courses/{id}/tasks", produces = "application/json")
public class TaskController {

    private final TaskService service;

    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Not Found"),
                    @ApiResponse(code = 200, message = "OK")
            })
    @GetMapping("/{taskId}")
    public TaskDto receiveTaskById(@PathVariable int id, @PathVariable int taskId) {
        var task = service.receiveTaskById(id, taskId);
        return transformTaskIntoDto(task);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Not Found"),
                    @ApiResponse(code = 200, message = "OK")
            })
    @GetMapping
    public Collection<TaskDto> receiveTasksByCourse(@PathVariable int id) {
        var tasks = service.receiveAllTasksByCourse(id);
        return transformTaskIntoDto(tasks);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Task is created successfully"),
                    @ApiResponse(code = 401, message = "Unauthorized")
            })
    @PreAuthorize("hasAuthority('admins')")
    @PostMapping
    public void addTask(@PathVariable int id, @RequestBody TaskDto taskDto) {
        service.addTask(id, taskDto);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Task was deleted successfully"),
                    @ApiResponse(code = 401, message = "Unauthorized")
            })
    @PreAuthorize("hasAuthority('admins')")
    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable int id, @PathVariable int taskId) {
        service.deleteTask(id, taskId);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Task was updated successfully"),
                    @ApiResponse(code = 401, message = "Unauthorized")
            })
    @PreAuthorize("hasAuthority('admins')")
    @PutMapping("/{taskId}")
    public TaskDto editTask(
            @PathVariable int id, @PathVariable int taskId, @RequestBody TaskDto taskDto) {
        var task = service.editTask(id, taskDto, taskId);
        return transformTaskIntoDto(task);
    }

    private TaskDto transformTaskIntoDto(Task task) {
        var taskDto = new TaskDto(task.getName(), task.getDescription());
        return taskDto;
    }

    private Collection<TaskDto> transformTaskIntoDto(Collection<Task> tasks) {
        var tasksDto =
                tasks.stream().map(task -> new TaskDto(task.getName(), task.getDescription())).collect(Collectors.toList());
        return tasksDto;
    }
}
