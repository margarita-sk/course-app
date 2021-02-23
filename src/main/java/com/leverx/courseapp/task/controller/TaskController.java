package com.leverx.courseapp.task.controller;

import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.dto.TaskDtoShort;
import com.leverx.courseapp.task.model.Task;
import com.leverx.courseapp.task.service.TaskService;

import java.util.Collection;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Api(value = "tasks")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping(value = "/courses/{id}/tasks", produces = "application/json")
public class TaskController {

    private final TaskService service;

    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/{taskId}")
    public TaskDto receiveTaskById(@PathVariable int id, @PathVariable int taskId) {
        var task = service.receiveTaskById(id, taskId);
        return task;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping
    public Collection<TaskDtoShort> receiveTasksByCourse(@PathVariable int id) {
        var tasks = service.receiveAllTasksByCourse(id);
        return tasks;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Task is created successfully"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping
    public void addTask(@PathVariable int id, @RequestBody TaskDto taskDto) {
        service.addTask(id, taskDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task was deleted successfully"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable int id, @PathVariable int taskId) {
        service.deleteTask(id, taskId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task was updated successfully"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PutMapping("/{taskId}")
    public TaskDto editTask(
            @PathVariable int id, @PathVariable int taskId, @RequestBody TaskDto taskDto) {
        return service.editTask(id, taskDto, taskId);
    }
}
