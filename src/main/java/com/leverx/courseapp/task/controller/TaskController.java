package com.leverx.courseapp.task.controller;

import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.model.Task;
import com.leverx.courseapp.task.service.TaskService;

import java.util.Collection;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping("/courses/{id}/tasks/{taskId}")
    public Task receiveTaskById(@PathVariable int id, @PathVariable int taskId) {
        var task = service.receiveTaskById(id, taskId);
        return task;
    }

    @GetMapping("/courses/{id}/tasks")
    public Collection<Task> receiveTasksByCourse(@PathVariable int id) {
        var tasks = service.receiveAllTasksByCourse(id);
        return tasks;
    }

    @PostMapping("/courses/{id}/tasks")
    public void addTask(@PathVariable int id, @ModelAttribute TaskDto taskDto) {
        service.addTask(id, taskDto);
    }

    @DeleteMapping("/courses/{id}/tasks/{taskId}")
    public void deleteTask(@PathVariable int id, @PathVariable int taskId) {
        service.deleteTask(id, taskId);
    }

    @PutMapping("/courses/{id}/task/{taskId}")
    public void editTask(@PathVariable int id, @PathVariable int taskId, @ModelAttribute TaskDto taskDto) {
        service.editTask(id, taskDto, taskId);
    }
}
