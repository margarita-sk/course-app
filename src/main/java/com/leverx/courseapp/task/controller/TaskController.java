package com.leverx.courseapp.task.controller;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.task.model.Task;
import com.leverx.courseapp.task.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class TaskController {

    private TaskService service;

    @GetMapping("/task/{id}")
    public Task receiveTaskById(@PathVariable int id) {
        return null;
    }

    @GetMapping("/tasks/{course}")
    public Collection<Task> receiveTasksByCourse(@PathVariable Course course) {
        return null;
    }

    @GetMapping("/tasks/{status}")
    public Collection<Task> receiveTasksByStatus(@PathVariable Task.Status status) {
        return null;
    }

    @PostMapping("/task")
    public void addTask(@PathVariable Task task) {

    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable int id) {

    }

    @PutMapping("/task/{id}")
    public void editTask(@PathVariable int id) {

    }

}
